package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import io.vertx.core.Future;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DecoratorEnactmentLogTest {

  protected static class MockFunction implements EnactmentFunction {

    protected final String typeId;
    protected final String implId;
    protected final int timeMillisecs;

    public MockFunction(String typeId, String implId, int timeMillisecs) {
      this.typeId = typeId;
      this.implId = implId;
      this.timeMillisecs = timeMillisecs;
    }

    @Override
    public Future<JsonObject> processInput(JsonObject input) {
      try {
        Thread.sleep(timeMillisecs);
      } catch (InterruptedException e) {
        fail();
      }
      return Future.succeededFuture(input);
    }

    @Override
    public String getTypeId() {
      return typeId;
    }

    @Override
    public String getEnactmentMode() {
      return EnactmentMode.Local.name();
    }

    @Override
    public String getImplementationId() {
      return implId;
    }

    @Override
    public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
      Set<SimpleEntry<String, String>> attr = new HashSet<>();
      attr.add(new SimpleEntry<String, String>("additional", "value1"));
      return attr;
    }
  }


  @Test
  public void testPreprocess() {
    String id = "id";
    String type = "type";
    int waitTime = 50;

    DecoratorEnactmentLogTest.MockFunction original =
        new DecoratorEnactmentLogTest.MockFunction(id, type, waitTime);
    EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);

    DecoratorEnactmentLog enactmentLog = new DecoratorEnactmentLog(original, enactmentLogger);

    JsonObject jsonObject = new JsonObject();
    jsonObject.add("testProp", new JsonPrimitive("testValue"));

    assertNull(enactmentLog.start);
    JsonObject returnedJsonObject = enactmentLog.preprocess(jsonObject).result();

    assertEquals(jsonObject, returnedJsonObject);
    assertNotNull(enactmentLog.start);
  }

  @Test
  public void testPostprocess() {
    String typeId = "typeId";
    String implId = "implementationId";
    int waitTime = 50;

    DecoratorEnactmentLogTest.MockFunction original =
        new DecoratorEnactmentLogTest.MockFunction(typeId, implId, waitTime);
    EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);

    DecoratorEnactmentLog enactmentLog = new DecoratorEnactmentLog(original, enactmentLogger);

    JsonObject jsonObject = new JsonObject();
    jsonObject.add("testProp", new JsonPrimitive("testValue"));

    Instant start = Instant.now();
    enactmentLog.start = start;

    try {
      Thread.sleep(waitTime);
    } catch (InterruptedException e) {
      fail();
    }
    JsonObject returnedJsonObject = enactmentLog.postprocess(jsonObject).result();

    ArgumentCaptor<EnactmentLogEntry> acEntry = ArgumentCaptor.forClass(EnactmentLogEntry.class);

    assertEquals(jsonObject, returnedJsonObject);
    verify(enactmentLogger).logEnactment((EnactmentLogEntry) acEntry.capture());

    EnactmentLogEntry capturedEntry = (EnactmentLogEntry) acEntry.getAllValues().get(0);
    assertEquals(typeId, capturedEntry.getTypeId());
    assertEquals(EnactmentMode.Local.name(), capturedEntry.getEnactmentMode());
    assertEquals(implId, capturedEntry.getImplementationId());
    assertTrue(capturedEntry.getExecutionTime() >= waitTime);
    assertEquals(true, capturedEntry.isSuccess());
    assertNotNull(capturedEntry.getTimestamp());
    assertEquals(1, capturedEntry.getAdditionalAttributes().size());
  }

}

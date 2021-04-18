package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DecoratorEnactmentLogTest {

  protected static class MockFunction implements EnactmentFunction {

    protected final String id;
    protected final String type;
    protected final int timeMillisecs;

    public MockFunction(String id, String type, int timeMillisecs) {
      this.id = id;
      this.type = type;
      this.timeMillisecs = timeMillisecs;
    }

    @Override
    public JsonObject processInput(JsonObject input) throws StopException {
      try {
        Thread.sleep(timeMillisecs);
      } catch (InterruptedException e) {
        fail();
      }
      return input;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public String getType() {
      return type;
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
    JsonObject returnedJsonObject = enactmentLog.preprocess(jsonObject);

    assertEquals(jsonObject, returnedJsonObject);
    assertNotNull(enactmentLog.start);
  }

  @Test
  public void testPostprocess() {
    String id = "id";
    String type = "type";
    int waitTime = 50;

    DecoratorEnactmentLogTest.MockFunction original =
        new DecoratorEnactmentLogTest.MockFunction(id, type, waitTime);
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
    JsonObject returnedJsonObject = enactmentLog.postprocess(jsonObject);

    ArgumentCaptor acEntry = ArgumentCaptor.forClass(EnactmentLogEntry.class);

    assertEquals(jsonObject, returnedJsonObject);
    verify(enactmentLogger).logEnactment((EnactmentLogEntry) acEntry.capture());

    EnactmentLogEntry capturedEntry = (EnactmentLogEntry) acEntry.getAllValues().get(0);
    assertEquals(id, capturedEntry.getFunctionId());
    assertEquals(type, capturedEntry.getFunctionType());
    assertEquals(waitTime, capturedEntry.getExecutionTime(), 1);
    assertEquals(true, capturedEntry.isSuccess());
  }

}

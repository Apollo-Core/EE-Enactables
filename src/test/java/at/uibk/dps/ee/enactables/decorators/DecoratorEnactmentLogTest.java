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

  @Test
  public void testPreprocess() {
    String id = "id";
    String type = "type";
    int waitTime = 50;

    DecoratorTimingLogTest.MockFunction
        original = new DecoratorTimingLogTest.MockFunction(id, type, waitTime);
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

    DecoratorTimingLogTest.MockFunction
        original = new DecoratorTimingLogTest.MockFunction(id, type, waitTime);
    EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);

    DecoratorEnactmentLog enactmentLog = new DecoratorEnactmentLog(original, enactmentLogger);

    EnactmentLogEntry entry = new EnactmentLogEntry(id, type, waitTime);

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
    assertEquals("id", capturedEntry.getId());
    assertEquals("type", capturedEntry.getType());
    assertEquals(50, capturedEntry.getExecutionTime(), 1);
  }

}

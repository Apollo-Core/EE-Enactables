package at.uibk.dps.ee.enactables.decorators.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import io.vertx.core.Future;
import com.google.gson.JsonObject;
import static org.junit.jupiter.api.Assertions.*;

public class DecoratorEnactmentLogTest {

  protected static class MockFunction implements EnactmentFunction {

    protected final String typeId;
    protected final String implId;
    protected final String functionId;
    protected final int timeMillisecs;

    public MockFunction(String typeId, String implId, String functionId, int timeMillisecs) {
      this.typeId = typeId;
      this.implId = implId;
      this.functionId = functionId;
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
  }


  // TODO these tests should be rewritten before using this class

  // @Test
  // public void testPreprocess() {
  // String id = "id";
  // String type = "type";
  // String funcId = "funcId";
  // int waitTime = 50;
  //
  // DecoratorEnactmentLogTest.MockFunction original =
  // new DecoratorEnactmentLogTest.MockFunction(id, type, funcId, waitTime);
  // EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);
  //
  // DecoratorEnactmentLog enactmentLog = new DecoratorEnactmentLog(original,
  // enactmentLogger);
  //
  // JsonObject jsonObject = new JsonObject();
  // jsonObject.add("testProp", new JsonPrimitive("testValue"));
  //
  // assertNull(enactmentLog.start);
  // JsonObject returnedJsonObject = enactmentLog.preprocess(jsonObject).result();
  //
  // assertEquals(jsonObject, returnedJsonObject);
  // assertNotNull(enactmentLog.start);
  // }
  //
  // @Test
  // public void testPostprocess() {
  // String typeId = "typeId";
  // String implId = "implementationId";
  // String funcId = "funcId";
  // int waitTime = 50;
  //
  // DecoratorEnactmentLogTest.MockFunction original =
  // new DecoratorEnactmentLogTest.MockFunction(typeId, implId, funcId, waitTime);
  // EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);
  //
  // DecoratorEnactmentLog enactmentLog = new DecoratorEnactmentLog(original,
  // enactmentLogger);
  //
  // JsonObject jsonObject = new JsonObject();
  // jsonObject.add("testProp", new JsonPrimitive("testValue"));
  //
  // Instant start = Instant.now();
  // enactmentLog.start = start;
  //
  // try {
  // Thread.sleep(waitTime);
  // } catch (InterruptedException e) {
  // fail();
  // }
  // JsonObject returnedJsonObject =
  // enactmentLog.postprocess(jsonObject).result();
  //
  // ArgumentCaptor<EnactmentLogEntry> acEntry =
  // ArgumentCaptor.forClass(EnactmentLogEntry.class);
  //
  // assertEquals(jsonObject, returnedJsonObject);
  // verify(enactmentLogger).logEnactment((EnactmentLogEntry) acEntry.capture());
  //
  // EnactmentLogEntry capturedEntry = (EnactmentLogEntry)
  // acEntry.getAllValues().get(0);
  // assertEquals(typeId, capturedEntry.getTypeId());
  // assertEquals(EnactmentMode.Local.name(), capturedEntry.getEnactmentMode());
  // assertEquals(implId, capturedEntry.getImplementationId());
  // assertTrue(capturedEntry.getExecutionTime() >= waitTime);
  // assertEquals(true, capturedEntry.isSuccess());
  // assertNotNull(capturedEntry.getTimestamp());
  // assertEquals(1, capturedEntry.getAdditionalAttributes().size());
  // }

}

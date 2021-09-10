package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

class DemoFunctionAbstractTest {

  @Test
  void testWaiting() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    MockDemoFunction tested = new MockDemoFunction(vertx);
    Instant start = Instant.now();
    JsonObject expected = new JsonObject();
    int waitInterval = 100;
    CountDownLatch cd = new CountDownLatch(1);
    tested.waitMilliseconds(expected, waitInterval).onComplete(asyncRes -> {
      assertTrue(asyncRes.succeeded());
      assertEquals(expected, asyncRes.result());
      cd.countDown();
    });
    cd.await();
    long duration = Duration.between(start, Instant.now()).toMillis();
    assertTrue(duration >= waitInterval);
  }
  
  
  protected class MockDemoFunction extends DemoFunctionAbstract{

    public MockDemoFunction(Vertx vertx) {
      super(null, null, null, null, vertx);
    }

    @Override
    protected Future<JsonObject> processVerifiedInput(JsonObject input)
        throws InputMissingException {
      return null;
    }
  }
}

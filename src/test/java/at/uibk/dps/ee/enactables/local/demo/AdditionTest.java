package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import io.vertx.core.Vertx;

public class AdditionTest {

  @Test
  @Timeout(value = 1, unit = TimeUnit.SECONDS)
  public void test() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    Addition tested = new Addition("id", "type", "task", vertx);
    JsonObject input = new JsonObject();
    input.addProperty(ConstantsLocal.inputAdditionFirst, 6);
    input.addProperty(ConstantsLocal.inputAdditionSecond, 7);
    input.addProperty(ConstantsLocal.inputWaitTime, 150);

    Instant before = Instant.now();
    CountDownLatch cd = new CountDownLatch(1);
    tested.processInput(input).onComplete(asyncRes -> {
      assertTrue(asyncRes.succeeded());
      assertEquals(13, asyncRes.result().get(ConstantsLocal.outputAdditionResult).getAsLong());
      cd.countDown();
    });
    cd.await();
    assertTrue(Duration.between(before, Instant.now()).toMillis() >= 150);
  }
}

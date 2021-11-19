package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class SubstractionTest {

  @Test
  @Timeout(value = 1, unit = TimeUnit.SECONDS)
  public void test() throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    Task task = new Task("task");
    Resource res = new Resource("res");
    Mapping<Task, Resource> mapping = new Mapping<>("map", task, res);
    FactoryInputUser finput = new FactoryInputUser(task, mapping);
    Subtraction tested = new Subtraction(finput, vertx);
    JsonObject input = new JsonObject();
    input.addProperty(ConstantsLocal.inputSubtractionMinuend, 6);
    input.addProperty(ConstantsLocal.inputSubtractionSubtrahend, 7);
    input.addProperty(ConstantsLocal.inputWaitTime, 150);

    Instant before = Instant.now();
    CountDownLatch cd = new CountDownLatch(1);
    tested.processInput(input).onComplete(asyncRes -> {
      assertTrue(asyncRes.succeeded());
      assertEquals(-1, asyncRes.result().get(ConstantsLocal.outputSubstractionResult).getAsLong());
      cd.countDown();
    });
    cd.await();
    assertTrue(Duration.between(before, Instant.now()).toMillis() >= 150);
  }
}

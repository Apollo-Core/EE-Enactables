package at.uibk.dps.ee.enactables.demo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class SumCollectionTest {

  @Test
  @Timeout(value = 2, unit = TimeUnit.SECONDS)
  public void test() throws InterruptedException {
    JsonArray collection = new JsonArray();
    JsonElement first = new JsonPrimitive(1);
    JsonElement second = new JsonPrimitive(3);
    JsonElement third = new JsonPrimitive(5);
    collection.add(first);
    collection.add(second);
    collection.add(third);

    JsonObject input = new JsonObject();
    input.add(ConstantsLocal.inputSumCollection, collection);
    JsonElement waitTime = new JsonPrimitive(150);
    input.add(ConstantsLocal.inputWaitTime, waitTime);
    Vertx vertx = Vertx.vertx();

    Task task = new Task("task");
    Resource res = new Resource("res");
    Mapping<Task, Resource> mapping = new Mapping<>("map", task, res);
    FactoryInputUser finput = new FactoryInputUser(task, mapping);
    SumCollection tested = new SumCollection(finput, vertx);
    Instant before = Instant.now();
    CountDownLatch cd = new CountDownLatch(1);
    tested.processInput(input).onComplete(asyncRes -> {
      assertTrue(asyncRes.succeeded());
      assertEquals(9, asyncRes.result().get(ConstantsLocal.outputSumCollection).getAsLong());
      cd.countDown();
    });
    cd.await();
    assertTrue(Duration.between(before, Instant.now()).toMillis() >= 150);
  }
}

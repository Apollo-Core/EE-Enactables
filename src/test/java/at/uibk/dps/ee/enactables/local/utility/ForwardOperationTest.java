package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

class ForwardOperationTest {

  ForwardOperation tested;
  JsonObject input;
  
  @Test
  void test() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    Future<JsonObject> result = tested.processInput(input);
    result.onComplete(asyncResult ->{
      assertEquals(input, result.result());
      latch.countDown();
    });
    latch.await();
  }
  
  @BeforeEach
  void setup() {
    Task task = new Task("task");
    tested = new ForwardOperation(task);
    input = new JsonObject();
  }

}

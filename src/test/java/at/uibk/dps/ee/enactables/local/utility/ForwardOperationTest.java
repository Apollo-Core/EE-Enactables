package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import io.vertx.core.Future;

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
    tested = new ForwardOperation();
    input = new JsonObject();
  }

}

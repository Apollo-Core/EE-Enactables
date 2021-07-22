package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class AdditionTest {

  @Test
  public void test() {

    Addition tested = new Addition("id", "type");
    JsonObject input = new JsonObject();
    input.addProperty(ConstantsLocal.inputAdditionFirst, 6);
    input.addProperty(ConstantsLocal.inputAdditionSecond, 7);
    input.addProperty(ConstantsLocal.inputWaitTime, 150);

    Instant before = Instant.now();
    JsonObject result;
    result = tested.processInput(input).result();
    Instant after = Instant.now();
    assertEquals(13, result.get(ConstantsLocal.outputAdditionResult).getAsLong());
    assertTrue(Duration.between(before, after).toMillis() >= 150);
  }
}

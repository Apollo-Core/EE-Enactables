package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

public class MultiplexerTest {

  @Test
  public void test() {
    Multiplexer tested = new Multiplexer("id", "type", "task");
    JsonObject inputTrue = new JsonObject();
    inputTrue.add(ConstantsEEModel.JsonKeyIfDecision, new JsonPrimitive(true));
    inputTrue.add(ConstantsEEModel.JsonKeyThen, new JsonPrimitive(42));
    JsonObject result = tested.processInput(inputTrue).result();
    assertEquals(42, result.get(ConstantsEEModel.JsonKeyIfResult).getAsInt());
    JsonObject inputFalse = new JsonObject();
    inputFalse.add(ConstantsEEModel.JsonKeyIfDecision, new JsonPrimitive(false));
    inputFalse.add(ConstantsEEModel.JsonKeyElse, new JsonPrimitive(false));
    JsonObject result2 = tested.processInput(inputFalse).result();
    assertFalse(result2.get(ConstantsEEModel.JsonKeyIfResult).getAsBoolean());
  }
}

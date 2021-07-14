package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class SplitArrayTest {

  @Test
  public void test() {

    JsonArray array = new JsonArray();
    array.add(1);
    array.add(2);
    array.add(3);
    array.add(4);
    JsonObject input = new JsonObject();
    input.add(ConstantsLocal.inputSplitArrayArray, array);
    input.add(ConstantsLocal.inputSplitArrayNumber, new JsonPrimitive(2));

    SplitArray tested = new SplitArray("id", "type");
    JsonObject result = tested.processInput(input).result();
    assertTrue(result.get(ConstantsLocal.outputSplitArray).isJsonArray());
    JsonArray resultArray = result.get(ConstantsLocal.outputSplitArray).getAsJsonArray();
    assertEquals(2, resultArray.size());
    JsonElement first = resultArray.get(0);
    JsonElement second = resultArray.get(1);
    assertEquals(1, first.getAsJsonArray().get(0).getAsNumber());
    assertEquals(2, first.getAsJsonArray().get(1).getAsNumber());
    assertEquals(3, second.getAsJsonArray().get(0).getAsNumber());
    assertEquals(4, second.getAsJsonArray().get(1).getAsNumber());

    // test input with not enough entries
    JsonObject input2 = new JsonObject();
    input2.add(ConstantsLocal.inputSplitArrayArray, array);
    input2.add(ConstantsLocal.inputSplitArrayNumber, new JsonPrimitive(6));
    JsonObject result2 = tested.processInput(input2).result();
    assertTrue(result2.get(ConstantsLocal.outputSplitArray).isJsonArray());
    JsonArray resultArray2 = result2.get(ConstantsLocal.outputSplitArray).getAsJsonArray();
    assertEquals(4, resultArray2.size());
    JsonElement first2 = resultArray2.get(0);
    JsonElement second2 = resultArray2.get(1);
    JsonElement third = resultArray2.get(2);
    JsonElement fourth = resultArray2.get(3);
    assertEquals(1, first2.getAsJsonArray().get(0).getAsNumber());
    assertEquals(2, second2.getAsJsonArray().get(0).getAsNumber());
    assertEquals(3, third.getAsJsonArray().get(0).getAsNumber());
    assertEquals(4, fourth.getAsJsonArray().get(0).getAsNumber());
  }
}

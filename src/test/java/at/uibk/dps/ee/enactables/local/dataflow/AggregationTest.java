package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

public class AggregationTest {

  @Test
  public void testCorrect() {
    Aggregation tested = new Aggregation("id", "type");
    String key = ConstantsEEModel.JsonKeyAggregation;

    JsonObject input = new JsonObject();
    input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(0));
    input.add(ConstantsEEModel.getCollectionElementKey(key, 1), new JsonPrimitive(1));
    input.add(ConstantsEEModel.getCollectionElementKey(key, 2), new JsonPrimitive(2));
    input.add(ConstantsEEModel.getCollectionElementKey(key, 3), new JsonPrimitive(3));

    JsonObject result;
    try {
      result = tested.processInput(input);
      assertTrue(result.has(key));
      JsonElement element = result.get(key);
      assertTrue(element.isJsonArray());
      JsonArray array = element.getAsJsonArray();

      assertEquals(0, array.get(0).getAsInt());
      assertEquals(1, array.get(1).getAsInt());
      assertEquals(2, array.get(2).getAsInt());
      assertEquals(3, array.get(3).getAsInt());
    } catch (StopException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInCorrect() {
    Aggregation tested = new Aggregation("id", "type");
    String key = ConstantsEEModel.JsonKeyAggregation;
    JsonObject input = new JsonObject();
    input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(0));
    input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(1));
    input.add("randomOtherKey", new JsonPrimitive(2));
    input.add(ConstantsEEModel.getCollectionElementKey(key, 0), new JsonPrimitive(3));
    try {
      tested.processInput(input);
    } catch (StopException e) {
      fail();
    }
  }
}

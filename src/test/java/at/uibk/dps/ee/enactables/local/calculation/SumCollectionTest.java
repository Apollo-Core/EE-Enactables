package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;

public class SumCollectionTest {

  @Test
  public void test() {
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


    SumCollection tested = new SumCollection();

    Instant before = Instant.now();
    JsonObject result;
    try {
      result = tested.processInput(input);
      Instant after = Instant.now();
      assertEquals(9, result.get(ConstantsLocal.outputSumCollection).getAsLong());
      assertTrue(Duration.between(before, after).toMillis() >= 150);
    } catch (StopException e) {
      fail();
    }
  }
}

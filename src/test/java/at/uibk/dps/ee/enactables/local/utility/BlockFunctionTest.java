package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

public class BlockFunctionTest {

  @Test
  public void testBlockResultEntryNumber() {
    String string = "2,0";
    JsonObject input = new JsonObject();
    CollOperBlock tested = new CollOperBlock(string, input);

    assertEquals(3, tested.getResultEntryNumber(6, 2, 0));
    assertEquals(2, tested.getResultEntryNumber(6, 3, 0));
    assertEquals(5, tested.getResultEntryNumber(6, 2, 1));
    assertEquals(3, tested.getResultEntryNumber(6, 3, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlockOneZero() {
    JsonObject input = new JsonObject();
    // create the input collection
    JsonArray array = new JsonArray();
    array.add(new JsonPrimitive(1));
    array.add(new JsonPrimitive(2));
    array.add(new JsonPrimitive(3));
    array.add(new JsonPrimitive(4));
    String someKey = "someKey";
    input.add(someKey, array);
    // create the task and annotate it with the subcollection
    String overlapSrc = "producer/output";
    String subCollString = "1," + overlapSrc;
    String dataId = "dataId";
    Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId,
        subCollString, CollectionOperation.Block);
    // enter the info on the stride into the input object
    String strideKey = overlapSrc;
    JsonElement strideNum = new JsonPrimitive(0);
    input.add(strideKey, strideNum);
    CollOperFunction tested = new CollOperFunction(task, "id", "type");
    try {
      tested.processInput(input);
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testBlockTwoOne() {
    JsonObject input = new JsonObject();
    // create the input collection
    JsonArray array = new JsonArray();
    array.add(new JsonPrimitive(1));
    array.add(new JsonPrimitive(2));
    array.add(new JsonPrimitive(3));
    array.add(new JsonPrimitive(4));
    String someKey = "someKey";
    input.add(someKey, array);
    // create the task and annotate it with the subcollection
    String sizeSrc = "producer/output";
    String subCollString = sizeSrc + ",1";
    String dataId = "dataId";
    Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId,
        subCollString, CollectionOperation.Block);
    // enter the info on the stride into the input object
    String strideKey = sizeSrc;
    JsonElement strideNum = new JsonPrimitive(2);
    input.add(strideKey, strideNum);
    CollOperFunction tested = new CollOperFunction(task, "id", "type");
    try {
      JsonObject jsonResult = tested.processInput(input);
      JsonElement result = jsonResult.get(someKey);
      assertTrue(result.isJsonArray());
      JsonArray resultArray = result.getAsJsonArray();
      assertEquals(3, resultArray.size());
      assertTrue(resultArray.get(0).isJsonArray());
      assertTrue(resultArray.get(1).isJsonArray());
      assertTrue(resultArray.get(2).isJsonArray());
      JsonArray array0 = resultArray.get(0).getAsJsonArray();
      assertEquals(1, array0.get(0).getAsInt());
      assertEquals(2, array0.get(1).getAsInt());
      JsonArray array1 = resultArray.get(1).getAsJsonArray();
      assertEquals(2, array1.get(0).getAsInt());
      assertEquals(3, array1.get(1).getAsInt());
      JsonArray array2 = resultArray.get(2).getAsJsonArray();
      assertEquals(3, array2.get(0).getAsInt());
      assertEquals(4, array2.get(1).getAsInt());
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testBlockThreeOne() {
    JsonObject input = new JsonObject();
    // create the input collection
    JsonArray array = new JsonArray();
    array.add(new JsonPrimitive(1));
    array.add(new JsonPrimitive(2));
    array.add(new JsonPrimitive(3));
    array.add(new JsonPrimitive(4));
    String someKey = "someKey";
    input.add(someKey, array);
    // create the task and annotate it with the subcollection
    String sizeSrc = "producer/output";
    String subCollString = sizeSrc + ",1";
    String dataId = "dataId";
    Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId,
        subCollString, CollectionOperation.Block);
    // enter the info on the stride into the input object
    String strideKey = sizeSrc;
    JsonElement strideNum = new JsonPrimitive(3);
    input.add(strideKey, strideNum);
    CollOperFunction tested = new CollOperFunction(task, "id", "type");
    try {
      JsonObject jsonResult = tested.processInput(input);
      JsonElement result = jsonResult.get(someKey);
      assertTrue(result.isJsonArray());
      JsonArray resultArray = result.getAsJsonArray();
      assertEquals(2, resultArray.size());
      assertTrue(resultArray.get(0).isJsonArray());
      assertTrue(resultArray.get(1).isJsonArray());
      JsonArray array0 = resultArray.get(0).getAsJsonArray();
      assertEquals(1, array0.get(0).getAsInt());
      assertEquals(2, array0.get(1).getAsInt());
      assertEquals(3, array0.get(2).getAsInt());
      JsonArray array1 = resultArray.get(1).getAsJsonArray();
      assertEquals(3, array1.get(0).getAsInt());
      assertEquals(4, array1.get(1).getAsInt());
    } catch (StopException e) {
      fail();
    }
  }
}

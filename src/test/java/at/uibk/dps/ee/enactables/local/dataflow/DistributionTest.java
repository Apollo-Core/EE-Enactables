package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class DistributionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectIterator() {
    Task funcNode = new Task("t");
    String key = ConstantsEEModel.JsonKeyConstantIterator;
    JsonObject jsonInput = new JsonObject();
    jsonInput.add(key, new JsonPrimitive("bla"));
    Distribution tested = new Distribution(funcNode);
    try {
      tested.processInput(jsonInput);
    } catch (StopException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInorrectIterator2() {
    Task funcNode = new Task("t");
    String key = "key";
    JsonObject jsonInput = new JsonObject();
    jsonInput.add(key, new JsonPrimitive("bla"));
    Distribution tested = new Distribution(funcNode);
    try {
      tested.processInput(jsonInput);
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testCorrectIntIterator() {
    Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
        OperationType.Distribution, "scope");
    String key = ConstantsEEModel.JsonKeyConstantIterator;
    JsonObject jsonInput = new JsonObject();
    jsonInput.add(key, new JsonPrimitive(5));
    Distribution tested = new Distribution(funcNode);
    try {
      JsonObject output = tested.processInput(jsonInput);
      assertEquals(5, PropertyServiceFunctionDataFlowCollections.getIterationNumber(funcNode));
      assertTrue(output
          .get(
              ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 0))
          .getAsBoolean());
      assertTrue(output
          .get(
              ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 1))
          .getAsBoolean());
      assertTrue(output
          .get(
              ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 2))
          .getAsBoolean());
      assertTrue(output
          .get(
              ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 3))
          .getAsBoolean());
      assertTrue(output
          .get(
              ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 4))
          .getAsBoolean());
    } catch (StopException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIntIteratorAndCollection() {
    Task funcNode = new Task("t");
    String key = ConstantsEEModel.JsonKeyConstantIterator;
    String key1 = "coll1";
    JsonObject jsonInput = new JsonObject();
    jsonInput.add(key, new JsonPrimitive(5));
    JsonArray array1 = new JsonArray();
    array1.add(1);
    array1.add(2);
    array1.add(3);
    jsonInput.add(key1, array1);
    Distribution tested = new Distribution(funcNode);
    try {
      tested.processInput(jsonInput);
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testCorrectCollections() {
    Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
        OperationType.Distribution, "scope");
    String key1 = "coll1";
    String key2 = "coll2";
    JsonObject jsonInput = new JsonObject();
    JsonArray array1 = new JsonArray();
    array1.add(1);
    array1.add(2);
    array1.add(3);
    JsonArray array2 = new JsonArray();
    array2.add("one");
    array2.add("two");
    array2.add("three");
    jsonInput.add(key1, array1);
    jsonInput.add(key2, array2);
    Distribution tested = new Distribution(funcNode);
    try {
      JsonObject output = tested.processInput(jsonInput);
      assertEquals(3, PropertyServiceFunctionDataFlowCollections.getIterationNumber(funcNode));
      assertEquals(1, output.get(ConstantsEEModel.getCollectionElementKey(key1, 0)).getAsInt());
      assertEquals(2, output.get(ConstantsEEModel.getCollectionElementKey(key1, 1)).getAsInt());
      assertEquals(3, output.get(ConstantsEEModel.getCollectionElementKey(key1, 2)).getAsInt());
      assertEquals("one",
          output.get(ConstantsEEModel.getCollectionElementKey(key2, 0)).getAsString());
      assertEquals("two",
          output.get(ConstantsEEModel.getCollectionElementKey(key2, 1)).getAsString());
      assertEquals("three",
          output.get(ConstantsEEModel.getCollectionElementKey(key2, 2)).getAsString());
    } catch (StopException e) {
      fail();
    }
  }

  @Test
  public void testCorrectOneCollection() {
    Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
        OperationType.Distribution, "scope");
    String key1 = "coll1";
    JsonObject jsonInput = new JsonObject();
    JsonArray array1 = new JsonArray();
    array1.add(1);
    array1.add(2);
    array1.add(3);
    jsonInput.add(key1, array1);
    Distribution tested = new Distribution(funcNode);
    try {
      JsonObject output = tested.processInput(jsonInput);
      assertEquals(3, PropertyServiceFunctionDataFlowCollections.getIterationNumber(funcNode));
      assertEquals(1, output.get(ConstantsEEModel.getCollectionElementKey(key1, 0)).getAsInt());
      assertEquals(2, output.get(ConstantsEEModel.getCollectionElementKey(key1, 1)).getAsInt());
      assertEquals(3, output.get(ConstantsEEModel.getCollectionElementKey(key1, 2)).getAsInt());
    } catch (StopException e) {
      fail();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testUnequalCollections() {
    Task funcNode = new Task("t");
    String key1 = "coll1";
    String key2 = "coll2";
    JsonObject jsonInput = new JsonObject();
    JsonArray array1 = new JsonArray();
    array1.add(1);
    array1.add(2);
    array1.add(3);
    JsonArray array2 = new JsonArray();
    array2.add("one");
    array2.add("two");
    array2.add("three");
    array2.add("four");
    jsonInput.add(key1, array1);
    jsonInput.add(key2, array2);
    Distribution tested = new Distribution(funcNode);
    try {
      tested.processInput(jsonInput);
    } catch (StopException e) {
      fail();
    }
  }
}

package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class DistributionTest {

  @Test
  public void testIncorrectIterator() {
    assertThrows(IllegalArgumentException.class, () -> {
      Task funcNode = new Task("t");
      String key = ConstantsEEModel.JsonKeyConstantIterator;
      JsonObject jsonInput = new JsonObject();
      jsonInput.add(key, new JsonPrimitive("bla"));
      Distribution tested = new Distribution(funcNode);
      tested.processInput(jsonInput);
    });
  }

  @Test
  public void testInorrectIterator2() {
    assertThrows(IllegalArgumentException.class, () -> {
      Task funcNode = new Task("t");
      String key = "key";
      JsonObject jsonInput = new JsonObject();
      jsonInput.add(key, new JsonPrimitive("bla"));
      Distribution tested = new Distribution(funcNode);
      tested.processInput(jsonInput);
    });
  }

  @Test
  public void testCorrectIntIterator() {
    Task funcNode = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t",
        OperationType.Distribution, "scope");
    String key = ConstantsEEModel.JsonKeyConstantIterator;
    JsonObject jsonInput = new JsonObject();
    jsonInput.add(key, new JsonPrimitive(5));
    Distribution tested = new Distribution(funcNode);
    JsonObject output = tested.processInput(jsonInput).result();
    assertEquals(5, PropertyServiceFunctionDataFlowCollections.getIterationNumber(funcNode));
    assertTrue(output
        .get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 0))
        .getAsBoolean());
    assertTrue(output
        .get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 1))
        .getAsBoolean());
    assertTrue(output
        .get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 2))
        .getAsBoolean());
    assertTrue(output
        .get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 3))
        .getAsBoolean());
    assertTrue(output
        .get(ConstantsEEModel.getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, 4))
        .getAsBoolean());
  }

  @Test
  public void testIntIteratorAndCollection() {
    assertThrows(IllegalArgumentException.class, () -> {
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
      tested.processInput(jsonInput);
    });
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
    JsonObject output = tested.processInput(jsonInput).result();
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
    JsonObject output = tested.processInput(jsonInput).result();
    assertEquals(3, PropertyServiceFunctionDataFlowCollections.getIterationNumber(funcNode));
    assertEquals(1, output.get(ConstantsEEModel.getCollectionElementKey(key1, 0)).getAsInt());
    assertEquals(2, output.get(ConstantsEEModel.getCollectionElementKey(key1, 1)).getAsInt());
    assertEquals(3, output.get(ConstantsEEModel.getCollectionElementKey(key1, 2)).getAsInt());
  }

  @Test
  public void testUnequalCollections() {
    assertThrows(IllegalStateException.class, () -> {
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
      tested.processInput(jsonInput);
    });
  }
}

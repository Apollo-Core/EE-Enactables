package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

public class ElementIndexFunctionTest {

  @Test
  public void test() {
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
    String strideSrc = "producer/output";
    String subCollString = "0, 1::" + strideSrc;
    String dataId = "dataId";
    Task task = PropertyServiceFunctionUtilityCollections.createCollectionOperation(dataId,
        subCollString, CollectionOperation.ElementIndex);
    // enter the info on the stride into the input object
    String strideKey = strideSrc;
    JsonElement strideNum = new JsonPrimitive(2);
    input.add(strideKey, strideNum);
    // create the object
    CollOperFunction tested = new CollOperFunction(task, "id", "type");
    JsonObject jsonResult = tested.processInput(input).result();
    JsonElement result = jsonResult.get(someKey);
    assertTrue(result.isJsonArray());
    JsonArray resultArray = result.getAsJsonArray();
    assertEquals(3, resultArray.size());
    assertEquals(1, resultArray.get(0).getAsInt());
    assertEquals(2, resultArray.get(1).getAsInt());
    assertEquals(4, resultArray.get(2).getAsInt());
  }
}

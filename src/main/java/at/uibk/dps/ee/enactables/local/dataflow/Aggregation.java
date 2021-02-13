package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Map.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * Performs the operation of aggregating multiple elements into a single
 * collection.
 * 
 * @author Fedor Smirnov
 *
 */
public class Aggregation extends LocalFunctionAbstract {

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final JsonArray array = new JsonArray();
    // fill the array, so that we can set indices
    for (int i = 0; i < input.size(); i++) {
      array.add(0);
    }
    for (final Entry<String, JsonElement> entry : input.entrySet()) {
      final String key = entry.getKey();
      final String collectionKey = ConstantsEEModel.getCollectionName(key);
      if (!collectionKey.equals(ConstantsEEModel.JsonKeyAggregation)) {
        throw new IllegalArgumentException("Wrong input for aggregation.");
      }
      final int idx = ConstantsEEModel.getArrayIndex(key);
      final JsonElement element = entry.getValue();
      array.set(idx, element);
    }
    final JsonObject result = new JsonObject();
    result.add(ConstantsEEModel.JsonKeyAggregation, array);
    return result;
  }
}

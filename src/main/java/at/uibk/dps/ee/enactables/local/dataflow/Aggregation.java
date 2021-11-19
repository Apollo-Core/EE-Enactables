package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Map.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * Performs the operation of aggregating multiple elements into a single
 * collection.
 * 
 * @author Fedor Smirnov
 *
 */
public class Aggregation extends FunctionAbstract {

  /**
   * Default constructor
   * 
   */
  public Aggregation(final Task task) {
    super(task);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
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
    return Future.succeededFuture(result);
  }
}

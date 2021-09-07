package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.HashSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import io.vertx.core.Future;

/**
 * The {@link EarliestArrival} function simply forwards its input (which is set
 * by the earliest of several potential inputs) to the output.
 * 
 * @author Fedor Smirnov
 */
public class EarliestArrival extends LocalFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the func id
   * @param type the func type
   */
  public EarliestArrival(final String idString, final String type, final String functionId) {
    super(idString, type, functionId, new HashSet<>());
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    final String key = ConstantsEEModel.EarliestArrivalJsonKey;
    // Get the input object
    checkInputEntry(input, key);
    final JsonElement element = input.get(key);
    // Put it into the output
    final JsonObject result = new JsonObject();
    result.add(key, element);
    return Future.succeededFuture(result);
  }
}

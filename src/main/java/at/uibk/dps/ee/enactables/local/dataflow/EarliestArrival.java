package at.uibk.dps.ee.enactables.local.dataflow;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * The {@link EarliestArrival} function simply forwards its input (which is set
 * by the earliest of several potential inputs) to the output.
 * 
 * @author Fedor Smirnov
 */
public class EarliestArrival extends FunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the func id
   * @param type the func type
   */
  public EarliestArrival(final Task task) {
    super(task);
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

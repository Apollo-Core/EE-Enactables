package at.uibk.dps.ee.enactables.local.dataflow;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * The {@link Multiplexer} forwards one of two inputs based on a decision
 * variable.
 * 
 * @author Fedor Smirnov
 *
 */
public class Multiplexer extends FunctionAbstract {

  /**
   * The default constructor.
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Multiplexer(Task task) {
    super(task);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    // get the decision variable
    checkInputEntry(input, ConstantsEEModel.JsonKeyIfDecision);
    final boolean decVar = input.get(ConstantsEEModel.JsonKeyIfDecision).getAsBoolean();
    // get the corresponding entry from the input
    final String resultKey = decVar ? ConstantsEEModel.JsonKeyThen : ConstantsEEModel.JsonKeyElse;
    checkInputEntry(input, resultKey);
    final JsonElement resultElement = input.get(resultKey);
    final JsonObject result = new JsonObject();
    result.add(ConstantsEEModel.JsonKeyIfResult, resultElement);
    return Future.succeededFuture(result);
  }
}

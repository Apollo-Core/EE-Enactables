package at.uibk.dps.ee.enactables.local.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.utility.CollOperSplit;
import io.vertx.core.Future;

/**
 * Local function to split a data array into a given number of sub arrays.
 * 
 * @author Fedor Smirnov
 *
 */
public class SplitArray extends FunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public SplitArray(FactoryInputUser input) {
    super(input.getTask(), input.getMapping());
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    final JsonArray jsonArray = readCollectionInput(input, ConstantsLocal.inputSplitArrayArray);
    final int splitNumber = readIntInput(input, ConstantsLocal.inputSplitArrayNumber);
    final CollOperSplit splitOperation = new CollOperSplit(splitNumber);
    final JsonElement subArrays = splitOperation.transformCollection(jsonArray);
    final JsonObject result = new JsonObject();
    result.add(ConstantsLocal.outputSplitArray, subArrays);
    return Future.succeededFuture(result);
  }
}

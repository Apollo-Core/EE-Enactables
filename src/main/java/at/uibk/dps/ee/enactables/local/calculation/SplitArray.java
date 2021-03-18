package at.uibk.dps.ee.enactables.local.calculation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.enactables.local.utility.CollOperSplit;

/**
 * Local function to split a data array into a given number of sub arrays.
 * 
 * @author Fedor Smirnov
 *
 */
public class SplitArray extends LocalFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public SplitArray(final String idString, final String type) {
    super(idString, type);
  }

  @Override
  public JsonObject processInput(final JsonObject input) throws StopException {
    final JsonArray jsonArray = readCollectionInput(input, ConstantsLocal.inputSplitArrayArray);
    final int splitNumber = readIntInput(input, ConstantsLocal.inputSplitArrayNumber);
    final CollOperSplit splitOperation = new CollOperSplit(splitNumber);
    final JsonElement subArrays = splitOperation.transformCollection(jsonArray);
    final JsonObject result = new JsonObject();
    result.add(ConstantsLocal.outputSplitArray, subArrays);
    return result;
  }
}

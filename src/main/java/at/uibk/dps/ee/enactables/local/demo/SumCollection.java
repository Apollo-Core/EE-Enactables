package at.uibk.dps.ee.enactables.local.demo;

import java.util.HashSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import io.vertx.core.Future;

/**
 * Processes a number collection by adding up all the numbers.
 * 
 * @author Fedor Smirnov
 */
public class SumCollection extends LocalFunctionAbstract {

  /**
   * The default constructor
   * 
   * @param idString the func id
   * @param type the function type
   */
  public SumCollection(final String idString, final String type) {
    super(idString, type, new HashSet<>());
  }

  @Override
  public Future<JsonObject> processInput(final JsonObject input) {
    final JsonArray jsonArray = readCollectionInput(input, ConstantsLocal.inputSumCollection);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    int result = 0;
    for (final JsonElement jsonElement : jsonArray) {
      result += jsonElement.getAsInt();
    }
    final JsonObject jsonResult = new JsonObject();
    jsonResult.add(ConstantsLocal.outputSumCollection, new JsonPrimitive(result));
    waitMilliseconds(waitTime);
    return Future.succeededFuture(jsonResult);
  }
}

package at.uibk.dps.ee.enactables.local.demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * Processes a number collection by adding up all the numbers.
 * 
 * @author Fedor Smirnov
 */
public class SumCollection extends DemoFunctionAbstract {

  /**
   * The default constructor
   * 
   * @param idString the func id
   * @param type the function type
   */
  public SumCollection(final FactoryInputUser input, final Vertx vertx) {
    super(input.getTask(), input.getMapping(), vertx);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    final JsonArray jsonArray = readCollectionInput(input, ConstantsLocal.inputSumCollection);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    int result = 0;
    for (final JsonElement jsonElement : jsonArray) {
      result += jsonElement.getAsInt();
    }
    final JsonObject jsonResult = new JsonObject();
    jsonResult.add(ConstantsLocal.outputSumCollection, new JsonPrimitive(result));
    return waitMilliseconds(jsonResult, waitTime);
  }
}

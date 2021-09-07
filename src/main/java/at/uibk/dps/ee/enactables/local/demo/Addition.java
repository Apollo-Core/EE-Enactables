package at.uibk.dps.ee.enactables.local.demo;

import java.util.HashSet;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import io.vertx.core.Future;

/**
 * Simple Addition of 2 inputs and an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Addition extends LocalFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Addition(final String idString, final String type, final String functionId) {
    super(idString, type, functionId, new HashSet<>());
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    final int firstSummand = readIntInput(input, ConstantsLocal.inputAdditionFirst);
    final int secondSummand = readIntInput(input, ConstantsLocal.inputAdditionSecond);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    final int sum = firstSummand + secondSummand;
    final JsonObject result = new JsonObject();
    result.addProperty(ConstantsLocal.outputAdditionResult, sum);
    waitMilliseconds(waitTime);
    return Future.succeededFuture(result);
  }
}


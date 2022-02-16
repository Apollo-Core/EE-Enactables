package at.uibk.dps.ee.enactables.demo;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.InputMissingException;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * Simple Addition of 2 inputs and an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Addition extends DemoFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Addition(final FactoryInputUser input, final Vertx vertx) {
    super(input.getTask(), input.getMapping(), vertx);
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
    return waitMilliseconds(result, waitTime);
  }
}


package at.uibk.dps.ee.enactables.local.demo;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.InputMissingException;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * Simple substraction of 2 inputs with an option to wait for a given number of
 * milliseconds.
 * 
 * @author Fedor Smirnov
 *
 */
public class Subtraction extends DemoFunctionAbstract {

  /**
   * Default constructor
   * 
   * @param idString the function id
   * @param type the function type
   */
  public Subtraction(final FactoryInputUser input, final Vertx vertx) {
    super(input.getTask(), input.getMapping(), vertx);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    final int minuend = readIntInput(input, ConstantsLocal.inputSubtractionMinuend);
    final int subtrahend = readIntInput(input, ConstantsLocal.inputSubtractionSubtrahend);
    final int waitTime = readIntInput(input, ConstantsLocal.inputWaitTime);
    final int difference = minuend - subtrahend;
    final JsonObject result = new JsonObject();
    result.addProperty(ConstantsLocal.outputSubstractionResult, difference);
    return waitMilliseconds(result, waitTime);
  }
}

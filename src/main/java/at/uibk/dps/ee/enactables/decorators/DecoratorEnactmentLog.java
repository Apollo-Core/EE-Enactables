package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.EnactmentFunctionDecorator;
import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.Instant;

/**
 * The {@link DecoratorEnactmentLog} is used to log information about the enactment of the
 * function it decorates.
 *
 * @author Markus Moosbrugger
 */
public class DecoratorEnactmentLog extends EnactmentFunctionDecorator {

  protected Instant start;
  protected EnactmentLogger enactmentLogger;

  /**
   * Default constructor.
   *
   * @param decoratedFunction the function whose execution properties are logged.
   */
  public DecoratorEnactmentLog(final EnactmentFunction decoratedFunction,
      EnactmentLogger enactmentLogger) {
    super(decoratedFunction);
    this.enactmentLogger = enactmentLogger;
  }

  @Override protected JsonObject preprocess(JsonObject input) {
    start = Instant.now();

    return input;
  }

  @Override protected JsonObject postprocess(JsonObject result) {
    // TODO set success in entry
    EnactmentLogEntry entry =
        new EnactmentLogEntry(decoratedFunction.getId(), decoratedFunction.getType(),
            Duration.between(start, Instant.now()).toMillis());
    enactmentLogger.logEnactment(entry);

    return result;
  }
}

package at.uibk.dps.ee.enactables.decorators.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.EnactmentFunctionDecorator;
import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import io.vertx.core.Future;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.Instant;

/**
 * The {@link DecoratorEnactmentLog} is used to log information about the
 * enactment of the function it decorates.
 *
 * @author Fedor Smirnov
 * @author Markus Moosbrugger
 */
public class DecoratorEnactmentLog extends EnactmentFunctionDecorator {

  protected Instant start;
  protected EnactmentLogger enactmentLogger;

  /**
   * Default constructor.
   *
   * @param decoratedFunction the function whose execution properties are
   *                          logged.
   */
  public DecoratorEnactmentLog(final EnactmentFunction decoratedFunction,
      final EnactmentLogger enactmentLogger) {
    super(decoratedFunction);
    this.enactmentLogger = enactmentLogger;
  }

  @Override
  protected Future<JsonObject> preprocess(final JsonObject input) {
    start = Instant.now();
    return Future.succeededFuture(input);
  }

  @Override
  protected Future<JsonObject> postprocess(final JsonObject result) {
    System.err.println("This one should be done asynchronously");
    final Instant now = Instant.now();
    final EnactmentLogEntry entry =
        new EnactmentLogEntry(now, decoratedFunction, Duration.between(start, now).toMillis(), true,
            0);
    enactmentLogger.logEnactment(entry);

    return Future.succeededFuture(result);
  }
}

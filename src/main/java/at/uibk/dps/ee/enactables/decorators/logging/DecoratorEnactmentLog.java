package at.uibk.dps.ee.enactables.decorators.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.EnactmentFunctionDecorator;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import at.uibk.dps.ee.enactables.logging.LoggingParamsExtractor;
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
  protected final EnactmentLogger enactmentLogger;
  protected final LoggingParamsExtractor parametersExtractor;

  /**
   * Default constructor.
   *
   * @param decoratedFunction the function whose execution properties are logged.
   */
  public DecoratorEnactmentLog(final EnactmentFunction decoratedFunction,
      final EnactmentLogger enactmentLogger, final LoggingParamsExtractor parametersExtractor) {
    super(decoratedFunction);
    this.enactmentLogger = enactmentLogger;
    this.parametersExtractor = parametersExtractor;
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
    final double execTime = Duration.between(start, now).toMillis();
    // TODO this one has to be adjusted
    final EnactmentLogEntry entry = parametersExtractor
        .extractLogEntry((FunctionAbstract) decoratedFunction, now, execTime, true, 1.0);
    enactmentLogger.logEnactment(entry);

    return Future.succeededFuture(result);
  }
}

package at.uibk.dps.ee.enactables.decorators.timeout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.EnactmentFunctionDecorator;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * The {@link DecoratorTimeout} lets the enactment of the underlying function
 * fail if it takes longer than a configured time.
 * 
 * @author Fedor Smirnov
 */
public class DecoratorTimeout extends EnactmentFunctionDecorator {

  protected final int timeOutMilliSeconds;
  protected final Vertx vertx;
  protected final Promise<JsonObject> resultPromise;

  protected final Logger logger = LoggerFactory.getLogger(DecoratorTimeout.class);

  /**
   * Injection constructor
   * 
   * @param decoratedFunction the function to decorate
   * @param timeOutMilliSeconds the timeout in milliseconds
   * @param vProv the vertx provider
   */
  public DecoratorTimeout(final EnactmentFunction decoratedFunction, final int timeOutMilliSeconds,
      final VertxProvider vProv) {
    super(decoratedFunction);
    this.timeOutMilliSeconds = timeOutMilliSeconds;
    this.vertx = vProv.getVertx();
    this.resultPromise = Promise.promise();
  }

  @Override
  public Future<JsonObject> processInput(final JsonObject input) {
    // case where we run into the timeout
    vertx.setTimer(timeOutMilliSeconds, timerId -> {
      if (resultPromise.tryFail("Time out exception of a function")) {
        logger.error("Timeout of the a function");
      }
    });
    // case where we get the result from the decorated function
    final Future<JsonObject> decoratedResult = super.processInput(input);
    decoratedResult.onComplete(asyncRes -> {
      if (asyncRes.succeeded()) {
        resultPromise.tryComplete(asyncRes.result());
      } else {
        resultPromise.tryFail(asyncRes.cause());
      }
    });
    return resultPromise.future();
  }

  @Override
  protected Future<JsonObject> preprocess(final JsonObject input) {
    return Future.succeededFuture(input);
  }

  @Override
  protected Future<JsonObject> postprocess(final JsonObject result) {
    return Future.succeededFuture(result);
  }
}

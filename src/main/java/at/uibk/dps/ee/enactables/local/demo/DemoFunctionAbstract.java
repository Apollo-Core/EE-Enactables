package at.uibk.dps.ee.enactables.local.demo;

import java.util.AbstractMap.SimpleEntry;
import com.google.gson.JsonObject;
import java.util.Set;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * Parent class for the user functions which are executed natively by Apollo
 * (used mainly for demo and test purposes).
 * 
 * @author Fedor Smirnov
 */
public abstract class DemoFunctionAbstract extends LocalFunctionAbstract {

  protected final Vertx vertx;

  /**
   * Constructor: Same as parent, but also get the vertx context to set timers.
   * 
   * @param implId
   * @param typeId
   * @param functionId
   * @param additionalAttrs
   * @param vertx
   */
  public DemoFunctionAbstract(final String implId, final String typeId, final String functionId,
      final Set<SimpleEntry<String, String>> additionalAttrs, final Vertx vertx) {
    super(implId, typeId, functionId, additionalAttrs);
    this.vertx = vertx;
  }

  /**
   * Returns a future which is completed with the given result after the given
   * amount of time.
   * 
   * @param result the result to complete the future with
   * @param milliseconds the wait interval
   * @return a future which is completed with the given result after the given
   *         amount of time
   */
  protected Future<JsonObject> waitMilliseconds(final JsonObject result, final int milliseconds) {
    final Promise<JsonObject> resultPromise = Promise.promise();
    vertx.setTimer(milliseconds, timerId -> {
      resultPromise.complete(result);
    });
    return resultPromise.future();
  }
}

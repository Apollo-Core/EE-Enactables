package at.uibk.dps.ee.enactables.local.demo;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * Parent class for the user functions which are executed natively by Apollo
 * (used mainly for demo and test purposes).
 * 
 * @author Fedor Smirnov
 */
public abstract class DemoFunctionAbstract extends FunctionAbstract {

  protected final Vertx vertx;

  /**
   * Constructor: Same as parent, but also get the vertx context to set timers.
   * 
   * @param vertx
   */
  public DemoFunctionAbstract(final Task task, final Mapping<Task, Resource> mapping, final Vertx vertx) {
    super(task, mapping);
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

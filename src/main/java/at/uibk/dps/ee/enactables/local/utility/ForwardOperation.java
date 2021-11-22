package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.enactables.InputMissingException;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * The {@link ForwardOperation} directly forwards the input to the output.
 * 
 * @author Fedor Smirnov
 *
 */
public class ForwardOperation extends FunctionAbstract {

  /**
   * Construction method.
   */
  public ForwardOperation(final Task task) {
    super(task);
  }

  /**
   * Returns a future which resolved to the provided input.
   */
  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    return Future.succeededFuture(input);
  }
}

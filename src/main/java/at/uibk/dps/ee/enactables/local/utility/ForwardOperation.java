package at.uibk.dps.ee.enactables.local.utility;

import java.util.HashSet;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import io.vertx.core.Future;

/**
 * The {@link ForwardOperation} directly forwards the input to the output.
 * 
 * @author Fedor Smirnov
 *
 */
public class ForwardOperation extends LocalFunctionAbstract {

  /**
   * Construction method.
   */
  public ForwardOperation() {
    super(UtilityType.While.name(), UtilityType.While.name(), UtilityType.While.name(),
        new HashSet<>());
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

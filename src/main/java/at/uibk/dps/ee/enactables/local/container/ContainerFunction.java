package at.uibk.dps.ee.enactables.local.container;

import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.ContainerManager;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 * The {@link ContainerFunction} is used to execute function within local Docker
 * containers.
 * 
 * @author Fedor Smirnov
 */
public class ContainerFunction extends FunctionAbstract {

  protected final ContainerManager containerManager;
  protected final String imageName;

  protected final Vertx vertx;

  /**
   * Injection constructor.
   * 
   * @param typeId the type id of the functions
   * @param implId the id of the implementation (with the id of the resource
   *        running the container)
   * @param additionalAttrubutes additional attributes
   * @param containerManager the class managing the containers
   * @param imageName the Docker image of the function
   */
  public ContainerFunction(final String typeId, final String implId, final String functionId,
      final Set<SimpleEntry<String, String>> additionalAttrubutes,
      final ContainerManager containerManager, final String imageName, final Vertx vertx) {
    super(typeId, implId, EnactmentMode.Local.name(), functionId, additionalAttrubutes);
    this.containerManager = containerManager;
    this.imageName = imageName;
    this.vertx = vertx;
  }

  @Override
  public Future<JsonObject> processInput(final JsonObject input) {
    return containerManager.runImage(imageName, input);
  }
}

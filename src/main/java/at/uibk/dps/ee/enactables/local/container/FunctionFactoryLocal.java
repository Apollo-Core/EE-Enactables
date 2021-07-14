package at.uibk.dps.ee.enactables.local.container;

import java.util.HashSet;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.docker.manager.ContainerManager;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMappingLocal;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * The {@link FunctionFactoryLocal} provides the functions modeling function
 * execution within local containers.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryLocal extends FunctionFactory<Mapping<Task, Resource>, ContainerFunction> {

  protected final ContainerManager containerManager;
  protected final Vertx vertx;

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryLocal(final Set<FunctionDecoratorFactory> decoratorFactories,
      final ContainerManager containerManager, VertxProvider vProv) {
    super(decoratorFactories);
    this.containerManager = containerManager;
    this.vertx = vProv.getVertx();
  }
  
  @Override
  protected ContainerFunction makeActualFunction(Mapping<Task, Resource> containerMapping) {
    final Task task = containerMapping.getSource();
    final String imageName = PropertyServiceMappingLocal.getImageName(containerMapping);
    final String typeId = PropertyServiceFunctionUser.getTypeId(task);
    final String implId = PropertyServiceMapping.getImplementationId(containerMapping);
    return new ContainerFunction(typeId, implId, new HashSet<>(), containerManager, imageName, vertx);
  }
}

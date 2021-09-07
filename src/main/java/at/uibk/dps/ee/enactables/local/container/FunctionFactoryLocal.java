package at.uibk.dps.ee.enactables.local.container;

import java.util.HashSet;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.ContainerManager;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.guice.container.ContainerManagerProvider;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMappingLocal;
import io.vertx.core.Vertx;

/**
 * The {@link FunctionFactoryLocal} provides the functions modeling function
 * execution within local containers.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryLocal extends FunctionFactory<FactoryInputUser, ContainerFunction> {

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
      final ContainerManagerProvider containerManagerProvider, final VertxProvider vProv) {
    super(decoratorFactories);
    this.containerManager = containerManagerProvider.getContainerManager();
    this.vertx = vProv.getVertx();
  }

  @Override
  protected ContainerFunction makeActualFunction(final FactoryInputUser input) {
    final String imageName = PropertyServiceMappingLocal.getImageName(input.getMapping());
    final String typeId = PropertyServiceFunctionUser.getTypeId(input.getTask());
    final String implId = PropertyServiceMapping.getImplementationId(input.getMapping());
    final String functionId = input.getTask().getId();
    return new ContainerFunction(typeId, implId, functionId, new HashSet<>(), containerManager,
        imageName, vertx);
  }
}

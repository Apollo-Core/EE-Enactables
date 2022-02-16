package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.enactables.FunctionFactoryUser;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import javax.inject.Inject;
import java.util.Set;

/**
 * The {@link FunctionFactoryServerless} creates the {@link EnactmentFunction}s
 * modeling the enactment of a serverless function.
 *
 * @author Fedor Smirnov
 */
public class FunctionFactoryServerless extends FunctionFactoryUser {

  protected final VertxProvider vProv;

  /**
   * Injection constructor.
   *
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryServerless(final Set<FunctionDecoratorFactory> decoratorFactories,
      final VertxProvider vProv) {
    super(decoratorFactories);
    this.vProv = vProv;
  }

  @Override
  protected EnactmentFunction makeActualFunction(final FactoryInputUser input) {
    return new ServerlessFunction(input.getTask(), input.getMapping(), vProv.getWebClient());
  }

  @Override
  public boolean isApplicable(FactoryInputUser factoryInput) {
    return PropertyServiceMapping.getEnactmentMode(factoryInput.getMapping())
        .equals(EnactmentMode.Serverless);
  }
}

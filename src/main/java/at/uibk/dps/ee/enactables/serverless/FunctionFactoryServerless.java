package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import javax.inject.Inject;
import java.util.Set;

/**
 * The {@link FunctionFactoryServerless} creates the {@link EnactmentFunction}s
 * modeling the enactment of a serverless function.
 *
 * @author Fedor Smirnov
 */
public class FunctionFactoryServerless
    extends FunctionFactory<Mapping<Task, Resource>, EnactmentFunction> {

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
  protected EnactmentFunction makeActualFunction(final Mapping<Task, Resource> input) {
    return new ServerlessFunction(input, vProv.getWebClient());
  }
}

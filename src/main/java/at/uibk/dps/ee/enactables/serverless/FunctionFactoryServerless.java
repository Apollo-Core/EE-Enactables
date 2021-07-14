package at.uibk.dps.ee.enactables.serverless;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import io.vertx.ext.web.client.WebClient;
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
public class FunctionFactoryServerless extends FunctionFactory {

  protected final WebClient client;
  
  /**
   * Injection constructor.
   *
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryServerless(final Set<FunctionDecoratorFactory> decoratorFactories, VertxProvider vProv) {
    super(decoratorFactories);
    this.client = vProv.getWebClient();
  }

  /**
   * Creates the {@link ServerlessFunction} which is modeled by the provided
   * resource node, decorated with the injected decorators.
   *
   * @param mapping the provided mapping edge
   * @return the {@link ServerlessFunction} which is modeled by the provided
   *         resource node, decorated with the injected decorators
   */
  public EnactmentFunction createServerlessFunction(final Mapping<Task, Resource> mapping) {
    final EnactmentFunction serverlessFunction = new ServerlessFunction(mapping, client);
    return decorate(serverlessFunction);
  }
}

package at.uibk.dps.ee.enactables.decorators.timeout;

import org.opt4j.core.start.Constant;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;
import at.uibk.dps.ee.guice.starter.VertxProvider;

/**
 * Factory to wrap function in {@link DecoratorTimeout}s.
 * 
 * @author Fedor Smirnov
 */
public class DecoratorTimeoutFactory extends FunctionDecoratorFactory {

  protected final int timeOutSeconds;
  protected final VertxProvider vProv;

  /**
   * The injection constructor.
   * 
   * @param vProv the vertx provider
   * @param timeOutSeconds the timeout in seconds
   */
  @Inject
  public DecoratorTimeoutFactory(final VertxProvider vProv, @Constant(value = "timeoutMilliSeconds",
      namespace = DecoratorTimeoutFactory.class) final int timeOutSeconds) {
    this.vProv = vProv;
    this.timeOutSeconds = timeOutSeconds;
  }

  @Override
  public EnactmentFunction decorateFunction(final EnactmentFunction function) {
    return new DecoratorTimeout(function, timeOutSeconds, vProv);
  }

  @Override
  public int getPriority() {
    // Always applied last
    return -Integer.MAX_VALUE;
  }
}

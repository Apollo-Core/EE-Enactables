package at.uibk.dps.ee.enactables.decorators.modules;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.start.Constant;
import at.uibk.dps.ee.enactables.decorators.timeout.DecoratorTimeoutFactory;
import at.uibk.dps.ee.guice.modules.FunctionModule;

/**
 * Module for the configuration of the timeout decorator.
 * 
 * @author Fedor Smirnov
 *
 */
public class FunctionTimeoutModule extends FunctionModule {

  @Order(1)
  @Info("The time after which an exception is thrown [ms]")
  @Constant(value = "timeoutMilliSeconds", namespace = DecoratorTimeoutFactory.class)
  public int timeOutMilliSeconds = 3000;

  @Override
  protected void config() {
    addFunctionDecoratorFactory(DecoratorTimeoutFactory.class);
  }

  public int getTimeOutMilliSeconds() {
    return timeOutMilliSeconds;
  }

  public void setTimeOutMilliSeconds(final int timeOutMilliSeconds) {
    this.timeOutMilliSeconds = timeOutMilliSeconds;
  }
}

package at.uibk.dps.ee.enactables.modules;

import org.opt4j.core.config.annotations.Category;
import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import at.uibk.dps.ee.enactables.FunctionFactoryUser;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;
import at.uibk.dps.ee.guice.modules.EeModule;

/**
 * Parent class for the modules configuring the enactment functions.
 * 
 * @author Fedor Smirnov
 */
@Category("Functions")
public abstract class FunctionModule extends EeModule {

  @Override
  protected void configureMultiBinders() {
    super.configureMultiBinders();
    multi(FunctionDecoratorFactory.class);
    multi(FunctionFactoryUser.class);
  }

  /**
   * Adds a function factory for a specific type of user functions.
   * 
   * @param functionFactoryUser the user function factory to add
   */
  public void addFunctionFactoryUser(final Class<? extends FunctionFactoryUser> functionFactory) {
    addFunctionFactoryUser(binder(), functionFactory);
  }

  /**
   * Adds a function factory for a specific type of user functions.
   * 
   * @param binder the binder
   * @param functionFactoryUser the user function factory to add
   */
  public static void addFunctionFactoryUser(final Binder binder,
      final Class<? extends FunctionFactoryUser> functionFactory) {
    final Multibinder<FunctionFactoryUser> multiBinder =
        Multibinder.newSetBinder(binder, FunctionFactoryUser.class);
    multiBinder.addBinding().to(functionFactory);
  }

  /**
   * Adds a function decorator which is to be used when creating functions.
   * 
   * @param functionDecorator the decorator to add
   */
  public void addFunctionDecoratorFactory(
      final Class<? extends FunctionDecoratorFactory> functionDecorator) {
    addFunctionDecoratorFactory(binder(), functionDecorator);
  }

  /**
   * Adds a function decorator which is to be used when creating functions.
   * 
   * @param binder the binder
   * @param functionDecorator the decorator to add
   */
  public static void addFunctionDecoratorFactory(final Binder binder,
      final Class<? extends FunctionDecoratorFactory> functionDecorator) {
    final Multibinder<FunctionDecoratorFactory> multiBinder =
        Multibinder.newSetBinder(binder, FunctionDecoratorFactory.class);
    multiBinder.addBinding().to(functionDecorator);
  }
}

package at.uibk.dps.ee.enactables;

import java.util.Set;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;

/**
 * Abstract parent class for all factories which create functions processing
 * user code.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class FunctionFactoryUser
    extends FunctionFactory<FactoryInputUser, EnactmentFunction> {

  /**
   * Same as the parent constructor.
   */
  public FunctionFactoryUser(Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  /**
   * Returns true iff this factory can generate an enactment function for the
   * given input.
   * 
   * @param factoryInput the given factory input
   * @return true iff this factory can generate an enactment function for the
   *         given input
   */
  public abstract boolean isApplicable(FactoryInputUser factoryInput);
}

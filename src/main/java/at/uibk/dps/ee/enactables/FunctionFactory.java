package at.uibk.dps.ee.enactables;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;

/**
 * The {@link FunctionFactory} enables the injection of function decorators to
 * wrap the functions created by actual factories.
 * 
 * @author Fedor Smirnov
 */
public abstract class FunctionFactory {

  protected final List<FunctionDecoratorFactory> decoratorFactories;

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the injected set of decorators
   */
  public FunctionFactory(final Set<FunctionDecoratorFactory> decoratorFactories) {
    this.decoratorFactories = sortDecorators(decoratorFactories);
  }

  /**
   * Sorts the decorators according to their priority.
   * 
   * @param decoratorSet the injected set of decorators
   * @return a list of the decorators, sorted based on their priority in
   *         descending order.
   */
  protected final List<FunctionDecoratorFactory> sortDecorators(
      final Set<FunctionDecoratorFactory> decoratorSet) {
    return decoratorSet.stream().sorted((dec1, dec2) -> {
      return dec1.getPriority() <= dec2.getPriority() ? 1 : -1;
    }).collect(Collectors.toList());
  }

  /**
   * Decorates the given function by applying the decorators in the list in the
   * right order.
   * 
   * @param functionToDecorate the function to decorate
   * @return the decorated function
   */
  protected EnactmentFunction decorate(final EnactmentFunction functionToDecorate) {
    EnactmentFunction result = functionToDecorate;
    for (final FunctionDecoratorFactory decorator : decoratorFactories) {
      result = decorator.decorateFunction(result);
    }
    return result;
  }
}

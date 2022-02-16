package at.uibk.dps.ee.enactables;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;

/**
 * The {@link FunctionFactory} enables the injection of function decorators to
 * wrap the functions created by actual factories.
 * 
 * @param <I> The type of object taken as input
 * @param <F> The type of function produced as output
 * 
 * @author Fedor Smirnov
 */
public abstract class FunctionFactory<I extends Object, F extends EnactmentFunction> {

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
   * Returns the produced function, decorated by the configured decorators.
   * 
   * @param input the production input
   * @return the produced function, decorated by the configured decorators
   */
  public EnactmentFunction makeFunction(final I input) {
    final F actualFunction = makeActualFunction(input);
    return decorate(actualFunction);
  }

  /**
   * Produces the actual function.
   * 
   * @param input the input used to get the production info
   * @return the produced function
   */
  protected abstract F makeActualFunction(I input);

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
  protected EnactmentFunction decorate(final F functionToDecorate) {
    EnactmentFunction result = functionToDecorate;
    for (final FunctionDecoratorFactory decorator : decoratorFactories) {
      result = decorator.decorateFunction(result);
    }
    return result;
  }
}

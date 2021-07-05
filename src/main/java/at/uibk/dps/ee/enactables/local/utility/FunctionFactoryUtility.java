package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import net.sf.opendse.model.Task;

/**
 * The {@link FunctionFactoryUtility} is used to construct utility tasks.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryUtility extends FunctionFactory {

  /**
   * The injection constructor.
   * 
   * @param decoratorFactories the set of factories producing the decorators to
   *        wrap the created functions.
   */
  @Inject
  public FunctionFactoryUtility(final Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  /**
   * Returns the utility function for the given task, wrapped by the decorators
   * following the configuration.
   * 
   * @param task the given task
   * @return the utility function for the given task, wrapped by the decorators
   *         following the configuration.
   */
  public EnactmentFunction getUtilityFunction(final Task task) {
    final EnactmentFunction originalFunction = getOriginalFunction(task);
    return decorate(originalFunction);
  }

  /**
   * Returns the original utility function for the given task.
   * 
   * @param task the given task
   * @return the original utility function for the given task.
   */
  protected EnactmentFunction getOriginalFunction(final Task task) {
    final UtilityType utilType = PropertyServiceFunctionUtility.getUtilityType(task);
    if (utilType.equals(UtilityType.Condition)) {
      return new ConditionEvaluation(task, task.getId(), EnactmentMode.Utility.name());
    } else if (utilType.equals(UtilityType.CollectionOperation)) {
      return new CollOperFunction(task, task.getId(), EnactmentMode.Utility.name());
    } else {
      throw new IllegalArgumentException("Unknown utility type: " + utilType.name());
    }
  }
}

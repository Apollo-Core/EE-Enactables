package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility.UtilityType;
import net.sf.opendse.model.Task;

/**
 * The {@link FunctionFactoryUtility} is used to construct utility tasks.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryUtility extends FunctionFactory<Task, EnactmentFunction> {

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

  @Override
  protected EnactmentFunction makeActualFunction(final Task task) {
    final UtilityType utilType = PropertyServiceFunctionUtility.getUtilityType(task);
    if (utilType.equals(UtilityType.Condition)) {
      return new ConditionEvaluation(task);
    } else if (utilType.equals(UtilityType.While) || utilType.equals(UtilityType.Sequelizer)) {
      return new ForwardOperation(task);
    } else if (utilType.equals(UtilityType.CollectionOperation)) {
      return new CollOperFunction(task);
    } else {
      throw new IllegalArgumentException("Unknown utility type: " + utilType.name());
    }
  }
}

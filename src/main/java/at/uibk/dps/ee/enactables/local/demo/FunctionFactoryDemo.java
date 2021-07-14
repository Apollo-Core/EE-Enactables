package at.uibk.dps.ee.enactables.local.demo;

import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;

/**
 * The {@link FunctionFactoryDemo} provides the enactment functions modeling
 * local operations.
 * 
 * @author Fedor Smirnov
 *
 */
public class FunctionFactoryDemo
    extends FunctionFactory<Mapping<Task, Resource>, EnactmentFunction> {

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryDemo(final Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  @Override
  protected EnactmentFunction makeActualFunction(Mapping<Task, Resource> input) {
    final Task task = input.getSource();
    final LocalCalculations localCalcs =
        LocalCalculations.valueOf(PropertyServiceFunctionUser.getTypeId(task));
    return getOriginalFunction(localCalcs);
  }

  /**
   * Returns the local function for the given enum.
   * 
   * @param localFunction the local function enum
   * @return the local function for the enum
   */
  protected LocalFunctionAbstract getOriginalFunction(final LocalCalculations localFunction) {
    switch (localFunction) {
      case Addition:
        return new Addition(LocalCalculations.Addition.name(), EnactmentMode.Local.name());
      case Subtraction:
        return new Subtraction(LocalCalculations.Subtraction.name(), EnactmentMode.Local.name());
      case SumCollection:
        return new SumCollection(LocalCalculations.SumCollection.name(),
            EnactmentMode.Local.name());
      case SplitArray:
        return new SplitArray(LocalCalculations.SplitArray.name(), EnactmentMode.Local.name());
      default:
        throw new IllegalArgumentException("Unknown local function " + localFunction.name());
    }
  }
}

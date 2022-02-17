package at.uibk.dps.ee.enactables.demo;

import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Task;
import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactoryUser;
import at.uibk.dps.ee.enactables.decorators.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.demo.ConstantsLocal.LocalCalculations;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.guice.starter.VertxProvider;

/**
 * The {@link FunctionFactoryDemo} provides the enactment functions modeling
 * local operations.
 * 
 * @author Fedor Smirnov
 *
 */
public class FunctionFactoryDemo extends FunctionFactoryUser {

  protected final Vertx vertx;

  /**
   * Injection constructor.
   * 
   * @param decoratorFactories the factories for the decorators which are used to
   *        wrap the created functions
   */
  @Inject
  public FunctionFactoryDemo(final Set<FunctionDecoratorFactory> decoratorFactories,
      final VertxProvider vProv) {
    super(decoratorFactories);
    this.vertx = vProv.getVertx();
  }

  @Override
  protected EnactmentFunction makeActualFunction(final FactoryInputUser input) {
    final Task task = input.getTask();
    final LocalCalculations localCalcs =
        LocalCalculations.valueOf(PropertyServiceFunctionUser.getTypeId(task));
    switch (localCalcs) {
      case Addition:
        return new Addition(input, vertx);
      case Subtraction:
        return new Subtraction(input, vertx);
      case SumCollection:
        return new SumCollection(input, vertx);
      case SplitArray:
        return new SplitArray(input);
      default:
        throw new IllegalArgumentException("Unknown local function " + localCalcs.name()
            + " specified for function " + task.getId());
    }
  }

  @Override
  public boolean isApplicable(final FactoryInputUser factoryInput) {
    return PropertyServiceMapping.getEnactmentMode(factoryInput.getMapping())
        .equals(EnactmentMode.Demo);
  }
}

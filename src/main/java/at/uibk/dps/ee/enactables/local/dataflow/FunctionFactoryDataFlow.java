package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

/**
 * The {@link FunctionFactoryDataFlow} is used to create the data flow
 * functions.
 * 
 * @author Fedor Smirnov
 */
public class FunctionFactoryDataFlow extends FunctionFactory<Task, EnactmentFunction> {

  /**
   * The injection constructor
   * 
   * @param decoratorFactories the factories for the decorators to wrap the
   *        created functions
   */
  @Inject
  public FunctionFactoryDataFlow(final Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  @Override
  protected EnactmentFunction makeActualFunction(final Task task) {
    final DataFlowType dfType = PropertyServiceFunctionDataFlow.getDataFlowType(task);
    if (dfType.equals(DataFlowType.EarliestInput)) {
      return new EarliestArrival(task);
    } else if (dfType.equals(DataFlowType.Multiplexer)) {
      return new Multiplexer(task);
    } else if (dfType.equals(DataFlowType.Collections)) {
      final OperationType oType = PropertyServiceFunctionDataFlowCollections.getOperationType(task);
      if (oType.equals(OperationType.Aggregation)) {
        return new Aggregation(task);
      } else if (oType.equals(OperationType.Distribution)) {
        return new Distribution(task);
      } else {
        throw new IllegalArgumentException("Unknown coll operation type: " + oType.name());
      }
    } else {
      throw new IllegalArgumentException("Unknown data flow type: " + dfType.name());
    }
  }
}

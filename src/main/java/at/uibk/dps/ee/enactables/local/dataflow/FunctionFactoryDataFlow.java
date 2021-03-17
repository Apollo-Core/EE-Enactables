package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.Set;
import com.google.inject.Inject;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.FunctionFactory;
import at.uibk.dps.ee.enactables.FunctionTypes;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
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
public class FunctionFactoryDataFlow extends FunctionFactory {

  @Inject
  public FunctionFactoryDataFlow(Set<FunctionDecoratorFactory> decoratorFactories) {
    super(decoratorFactories);
  }

  /**
   * Returns the data flow function modeled by the given task, wrapped in
   * decorators according to the configuration.
   * 
   * @param task the task modeling the function
   * @return the data flow function modeled by the given task, wrapped in
   *         decorators according to the configuration
   */
  public EnactmentFunction getDataFlowFunction(Task task) {
    EnactmentFunction original = getOriginalFunction(task);
    return decorate(original);
  }

  /**
   * Returns the actual function modeled by the given task.
   * 
   * @param task the given task
   * @return the actual function modeled by the given task
   */
  protected LocalFunctionAbstract getOriginalFunction(Task task) {
    final DataFlowType dfType = PropertyServiceFunctionDataFlow.getDataFlowType(task);
    if (dfType.equals(DataFlowType.EarliestInput)) {
      return new EarliestArrival(DataFlowType.EarliestInput.name(), FunctionTypes.DataFlow.name());
    } else if (dfType.equals(DataFlowType.Multiplexer)) {
      return new Multiplexer(DataFlowType.Multiplexer.name(), FunctionTypes.DataFlow.name());
    } else if (dfType.equals(DataFlowType.Collections)) {
      final OperationType oType = PropertyServiceFunctionDataFlowCollections.getOperationType(task);
      if (oType.equals(OperationType.Aggregation)) {
        return new Aggregation(OperationType.Aggregation.name(), FunctionTypes.DataFlow.name());
      } else if (oType.equals(OperationType.Distribution)) {
        return new Distribution(task, OperationType.Distribution.name(),
            FunctionTypes.DataFlow.name());
      } else {
        throw new IllegalArgumentException("Unknown coll operation type: " + oType.name());
      }
    } else {
      throw new IllegalArgumentException("Unknown data flow type: " + dfType.name());
    }
  }



}

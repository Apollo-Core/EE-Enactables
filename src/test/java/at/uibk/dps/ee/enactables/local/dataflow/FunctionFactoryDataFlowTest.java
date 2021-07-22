package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlow.DataFlowType;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections.OperationType;
import net.sf.opendse.model.Task;

public class FunctionFactoryDataFlowTest {

  @Test
  public void test() {
    FunctionFactoryDataFlow tested = new FunctionFactoryDataFlow(new HashSet<>());
    Task earliestInTask =
        PropertyServiceFunctionDataFlow.createDataFlowFunction("t", DataFlowType.EarliestInput);
    Task aggrTask = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t1",
        OperationType.Aggregation, "scope");
    Task distTask = PropertyServiceFunctionDataFlowCollections.createCollectionDataFlowTask("t1",
        OperationType.Distribution, "scope");
    Task muxerTask =
        PropertyServiceFunctionDataFlow.createDataFlowFunction("t1", DataFlowType.Multiplexer);
    assertTrue(tested.makeFunction(earliestInTask) instanceof EarliestArrival);
    assertTrue(tested.makeFunction(distTask) instanceof Distribution);
    assertTrue(tested.makeFunction(aggrTask) instanceof Aggregation);
    assertTrue(tested.makeFunction(muxerTask) instanceof Multiplexer);
  }
}

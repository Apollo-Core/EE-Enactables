package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtility;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityWhile;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Task;

public class FunctionFactoryUtilityTest {

  @Test
  public void test() {
    FunctionFactoryUtility tested = new FunctionFactoryUtility(new HashSet<>());
    List<Condition> conditions = new ArrayList<>();
    Task conditionTask =
        PropertyServiceFunctionUtilityCondition.createConditionEvaluation("bla", conditions);
    Task collectionTask = PropertyServiceFunctionUtilityCollections.createCollectionOperation("bla",
        "blabla", CollectionOperation.Block);

    Task whileStart = new Task("whileStart");
    Task whileCounter = new Task("whileCounter");
    Task whileTask =
        PropertyServiceFunctionUtilityWhile.createWhileEndTask(whileStart, whileCounter);

    Communication comm = new Communication("c1");
    Communication comm2 = new Communication("c2");
    EnactmentGraph graph = new EnactmentGraph();
    Task sequelizer = PropertyServiceFunctionUtility.addSequelizerNode(comm, comm2, graph);

    assertTrue(tested.makeFunction(collectionTask) instanceof CollOperFunction);
    assertTrue(tested.makeFunction(conditionTask) instanceof ConditionEvaluation);
    assertTrue(tested.makeFunction(whileTask) instanceof ForwardOperation);
    assertTrue(tested.makeFunction(sequelizer) instanceof ForwardOperation);
  }
}

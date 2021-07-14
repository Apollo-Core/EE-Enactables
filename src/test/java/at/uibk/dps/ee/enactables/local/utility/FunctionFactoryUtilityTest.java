package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
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
    assertTrue(tested.makeFunction(collectionTask) instanceof CollOperFunction);
    assertTrue(tested.makeFunction(conditionTask) instanceof ConditionEvaluation);
  }
}

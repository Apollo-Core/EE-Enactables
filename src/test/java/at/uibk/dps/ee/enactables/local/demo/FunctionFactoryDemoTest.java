package at.uibk.dps.ee.enactables.local.demo;

import static org.junit.Assert.*;
import java.util.HashSet;
import org.junit.Test;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class FunctionFactoryDemoTest {

  @Test
  public void test() {
    FunctionFactoryDemo tested = new FunctionFactoryDemo(new HashSet<>());
    Resource res = new Resource("res");
    Task task =
        PropertyServiceFunctionUser.createUserTask("task", LocalCalculations.Addition.name());
    Mapping<Task, Resource> additionMapping =
        PropertyServiceMapping.createMapping(task, res, EnactmentMode.Demo, "demo");
    assertTrue(tested.makeFunction(additionMapping) instanceof Addition);

    Task taskSub =
        PropertyServiceFunctionUser.createUserTask("task", LocalCalculations.Subtraction.name());
    Mapping<Task, Resource> subtractionMapping =
        PropertyServiceMapping.createMapping(taskSub, res, EnactmentMode.Demo, "demo");
    assertTrue(tested.makeFunction(subtractionMapping) instanceof Subtraction);

    Task taskSumCol =
        PropertyServiceFunctionUser.createUserTask("task", LocalCalculations.SumCollection.name());
    Mapping<Task, Resource> sumColMapping =
        PropertyServiceMapping.createMapping(taskSumCol, res, EnactmentMode.Demo, "demo");
    assertTrue(tested.makeFunction(sumColMapping) instanceof SumCollection);
  }
}

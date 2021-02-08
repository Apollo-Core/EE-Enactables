package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.enactables.local.ConstantsLocal;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.UsageType;
import net.sf.opendse.model.Task;

public class CalculationBuilderTest {

	@Test
	public void test() {
		CalculationBuilder tested = new CalculationBuilder();
		assertEquals(UsageType.Local, tested.getType());

		String name = ConstantsLocal.prefixAddition + "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		EnactableAtomic result = tested.buildEnactable(task, stateListeners);
		assertTrue(result instanceof Addition);

		String nameSubst = ConstantsLocal.prefixSubstraction + "blabla";
		task = new Task(nameSubst);

		result = tested.buildEnactable(task, stateListeners);
		assertTrue(result instanceof Subtraction);

		String nameSumColl = ConstantsLocal.prefixSumCollection + "bl";
		task = new Task(nameSumColl);

		result = tested.buildEnactable(task, stateListeners);
		assertTrue(result instanceof SumCollection);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnknownName() {
		CalculationBuilder tested = new CalculationBuilder();

		String name = "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();

		tested.buildEnactable(task, stateListeners);
	}
}

package at.uibk.dps.ee.enactables.local.calculation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.enactables.EnactableAtomic;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction.FunctionType;
import net.sf.opendse.model.Task;

public class CalculationBuilderTest {

	@Test
	public void test() {
		CalculationBuilder tested = new CalculationBuilder();
		assertEquals(FunctionType.Local, tested.getType());

		String name = ConstantsCalculation.prefixAddition + "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		EnactableAtomic result = tested.buildEnactable(task, inputKeys, stateListeners);
		assertTrue(result instanceof Addition);

		String nameSubst = ConstantsCalculation.prefixSubstraction + "blabla";
		task = new Task(nameSubst);

		result = tested.buildEnactable(task, inputKeys, stateListeners);
		assertTrue(result instanceof Substraction);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnknownName() {
		CalculationBuilder tested = new CalculationBuilder();

		String name = "bla";
		Task task = new Task(name);
		Set<EnactableStateListener> stateListeners = new HashSet<>();
		Set<String> inputKeys = new HashSet<>();

		tested.buildEnactable(task, inputKeys, stateListeners);
	}
}

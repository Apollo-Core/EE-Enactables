package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConditionCheckerBooleanTest {

  @Test
  public void testEqual() {
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    assertTrue(tested.equal(true, true));
    assertTrue(tested.equal(false, false));
    assertFalse(tested.equal(true, false));
    assertFalse(tested.equal(false, true));
  }

  @Test
  public void testExtractArgument() {
    JsonElement trueEl = new JsonPrimitive(true);
    JsonElement falseEl = new JsonPrimitive(false);
    ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
    assertEquals(true, tested.extractArgument(trueEl));
    assertEquals(false, tested.extractArgument(falseEl));
  }

  @Test
  public void testLess() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.less(true, true);
    });
  }

  @Test
  public void testLessEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.lessEqual(true, true);
    });
  }

  @Test
  public void testGreater() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.greater(true, true);
    });
  }

  @Test
  public void testGreaterEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.greaterEqual(true, true);
    });
  }

  @Test
  public void testContains() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.contains(true, true);
    });
  }

  @Test
  public void testStartsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.startsWith(true, true);
    });
  }

  @Test
  public void testEndsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerBoolean tested = new ConditionCheckerBoolean();
      tested.endsWith(true, true);
    });
  }
}

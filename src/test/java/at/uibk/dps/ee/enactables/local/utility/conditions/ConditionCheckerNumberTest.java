package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConditionCheckerNumberTest {

  @Test
  public void testExtractArgument() {
    ConditionCheckerNumber tested = new ConditionCheckerNumber();
    int num1 = 42;
    double num2 = 0.0001;
    JsonElement element1 = new JsonPrimitive(num1);
    JsonElement element2 = new JsonPrimitive(num2);
    JsonArray array = new JsonArray();
    array.add(element1);
    array.add(element2);
    assertEquals(num1, tested.extractArgument(element1).longValue());
    assertEquals(num2, tested.extractArgument(element2).doubleValue(), 0.000000000001);
    assertEquals(1, tested.extractArgument(array).longValue());
  }

  @Test
  public void testCorrect() {
    ConditionCheckerNumber tested = new ConditionCheckerNumber();
    assertTrue(tested.equal(42, 42));
    assertTrue(tested.less(41, 42));
    assertFalse(tested.less(42, 41));
    assertTrue(tested.lessEqual(41, 42));
    assertTrue(tested.greater(42, 41));
    assertTrue(tested.greaterEqual(42, 41));
    assertTrue(tested.greaterEqual(0.1000000001, 0.1000000002));
  }

  @Test
  public void testContains() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerNumber tested = new ConditionCheckerNumber();
      tested.contains(42, 41);
    });
  }

  @Test
  public void testStartsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerNumber tested = new ConditionCheckerNumber();
      tested.startsWith(42, 41);
    });
  }

  @Test
  public void testEndsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerNumber tested = new ConditionCheckerNumber();
      tested.endsWith(42, 41);
    });
  }
}

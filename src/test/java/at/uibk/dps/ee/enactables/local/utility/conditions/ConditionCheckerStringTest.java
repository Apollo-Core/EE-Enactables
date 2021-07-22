package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConditionCheckerStringTest {

  @Test
  public void testExtractArgument() {
    String first = "abc";
    String second = "hello";
    JsonElement firstEl = new JsonPrimitive(first);
    JsonElement secondEl = new JsonPrimitive(second);
    ConditionCheckerString tested = new ConditionCheckerString();
    assertEquals(first, tested.extractArgument(firstEl));
    assertEquals(second, tested.extractArgument(secondEl));
  }

  @Test
  public void testCorrect() {
    ConditionCheckerString tested = new ConditionCheckerString();
    assertTrue(tested.equal("abc", "abc"));
    assertFalse(tested.equal("abc", "abd"));
    assertTrue(tested.contains("abcd", "bc"));
    assertFalse(tested.contains("abcd", "bd"));
    assertTrue(tested.startsWith("abcd", "ab"));
    assertFalse(tested.startsWith("abcd", "bb"));
    assertTrue(tested.endsWith("abcd", "cd"));
    assertFalse(tested.endsWith("abcd", "ab"));
  }

  @Test
  public void testLess() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerString tested = new ConditionCheckerString();
      tested.less("abc", "ABC");
    });
  }

  @Test
  public void testLessEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerString tested = new ConditionCheckerString();
      tested.lessEqual("abc", "ABC");
    });
  }

  @Test
  public void testGreater() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerString tested = new ConditionCheckerString();
      tested.greater("abc", "ABC");
    });
  }

  @Test
  public void testGreaterEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerString tested = new ConditionCheckerString();
      tested.greaterEqual("abc", "ABC");
    });
  }
}

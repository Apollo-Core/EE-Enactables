package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConditionCheckerObjectTest {

  @Test
  public void testEquals() {
    String firstString = "{ \"name\": \"Name1\", \"java\": true, \"answer\": 42 }";
    String secondString = "{ \"name\": \"Name1\", \"answer\": 42, \"java\": true }";
    String thirdString = "{ \"name\": \"Name\", \"answer\": 42, \"java\": true }";
    JsonElement firstElement = JsonParser.parseString(firstString);
    JsonElement secondElement = JsonParser.parseString(secondString);
    JsonElement thirdElement = JsonParser.parseString(thirdString);
    ConditionCheckerObject tested = new ConditionCheckerObject();
    assertTrue(tested.equal(firstElement, secondElement));
    assertFalse(tested.equal(firstElement, thirdElement));
  }

  @Test
  public void testExtractObject() {
    ConditionCheckerObject tested = new ConditionCheckerObject();
    JsonElement element = new JsonObject();
    assertEquals(element, tested.extractArgument(element));
  }

  @Test
  public void testLess() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.less(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testLessEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.lessEqual(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testGreater() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.greater(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testGreaterEqual() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.greaterEqual(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testContains() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.contains(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testStartsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.startsWith(new JsonObject(), new JsonObject());
    });
  }

  @Test
  public void testEndsWith() {
    assertThrows(IllegalArgumentException.class, () -> {
      ConditionCheckerObject tested = new ConditionCheckerObject();
      tested.endsWith(new JsonObject(), new JsonObject());
    });
  }
}

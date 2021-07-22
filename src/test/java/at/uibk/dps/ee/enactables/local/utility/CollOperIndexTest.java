package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

public class CollOperIndexTest {

  @Test
  public void test() {
    JsonArray input = new JsonArray();

    JsonElement el0 = mock(JsonElement.class);
    JsonElement el1 = mock(JsonElement.class);
    JsonElement el2 = mock(JsonElement.class);
    JsonElement el3 = mock(JsonElement.class);
    JsonElement el4 = mock(JsonElement.class);

    input.add(el0);
    input.add(el1);
    input.add(el2);
    input.add(el3);
    input.add(el4);

    CollOperIndex tested = new CollOperIndex(3);
    assertEquals(el3, tested.transformCollection(input));
    assertEquals("3", tested.toString());
  }
}

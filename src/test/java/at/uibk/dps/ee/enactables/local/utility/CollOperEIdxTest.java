package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;


public class CollOperEIdxTest {

  @Test
  public void testBasic() {
    CollOperEIdx tested = new CollOperEIdx();
    CollOper mock1 = mock(CollOper.class);
    CollOper mock2 = mock(CollOper.class);
    CollOper mock3 = mock(CollOper.class);
    String string1 = "bla";
    String string2 = "abc";
    String string3 = "fun";
    String expected = string1 + ',' + string2 + ',' + string3;

    when(mock1.toString()).thenReturn(string1);
    when(mock2.toString()).thenReturn(string2);
    when(mock3.toString()).thenReturn(string3);

    tested.add(mock1);
    tested.add(mock2);
    tested.add(mock3);

    assertEquals(expected, tested.toString());
  }

  @Test
  public void testProcess() {
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

    JsonElement return1 = el4;

    JsonArray return2 = new JsonArray();
    return2.add(el3);
    return2.add(el4);

    JsonArray return3 = new JsonArray();
    return3.add(el0);

    CollOper sub1 = mock(CollOper.class);
    CollOper sub2 = mock(CollOper.class);
    CollOper sub3 = mock(CollOper.class);

    when(sub1.transformCollection(input)).thenReturn(return1);
    when(sub2.transformCollection(input)).thenReturn(return2);
    when(sub3.transformCollection(input)).thenReturn(return3);

    CollOperEIdx tested = new CollOperEIdx();
    tested.add(sub1);
    tested.add(sub2);
    tested.add(sub3);

    JsonElement result = tested.transformCollection(input);
    assertTrue(result.isJsonArray());
    JsonArray resultArray = result.getAsJsonArray();
    assertEquals(4, resultArray.size());
    assertEquals(el4, resultArray.get(0));
    assertEquals(el3, resultArray.get(1));
    assertEquals(el4, resultArray.get(2));
    assertEquals(el0, resultArray.get(3));

    CollOperEIdx tested2 = new CollOperEIdx();
    tested2.add(sub2);

    JsonElement result2 = tested2.transformCollection(input);
    assertTrue(result2.isJsonArray());
    JsonArray resultArray2 = result2.getAsJsonArray();
    assertEquals(2, resultArray2.size());
    assertEquals(el3, resultArray2.get(0));
    assertEquals(el4, resultArray2.get(1));
  }
}

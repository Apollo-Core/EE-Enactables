package at.uibk.dps.ee.enactables.local;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.vertx.core.Future;

public class LocalFunctionAbstractTest {

  protected static class FunctionMock extends LocalFunctionAbstract {

    public FunctionMock() {
      super("id", "type", "funcID", new HashSet<>());
    }

    @Override
    public Future<JsonObject> processVerifiedInput(JsonObject input) throws InputMissingException {
      return Future.succeededFuture(input);
    }

  }

  @Test
  public void testCheckInputEntryTest() {
    assertThrows(InputMissingException.class, () -> {
      FunctionMock tested = new FunctionMock();
      tested.checkInputEntry(new JsonObject(), "key");
    });
  }

  @Test
  public void testReadIntEntry() throws InputMissingException {
    FunctionMock tested = new FunctionMock();
    JsonObject input = new JsonObject();
    input.addProperty("key", 42);
    assertEquals(42, (long) tested.readIntInput(input, "key"));
  }

  @Test
  public void testReadEntry() throws InputMissingException {
    FunctionMock tested = new FunctionMock();
    JsonObject input = new JsonObject();
    JsonElement value = new JsonPrimitive("string");
    input.add("key", value);
    assertEquals(value, tested.readEntry(input, "key"));
  }

  @Test
  public void testReadCollectionInputCorrect() throws InputMissingException {
    FunctionMock tested = new FunctionMock();
    JsonArray array = new JsonArray();
    String key = "key";
    JsonObject input = new JsonObject();
    input.add(key, array);
    assertEquals(array, tested.readCollectionInput(input, key));
  }

  @Test
  public void testReadCollectionInputIncorrect() {
    assertThrows(IllegalArgumentException.class, () -> {
      FunctionMock tested = new FunctionMock();
      JsonPrimitive primitive = new JsonPrimitive(42);
      String key = "key";
      JsonObject input = new JsonObject();
      input.add(key, primitive);
      assertEquals(primitive, tested.readCollectionInput(input, key));
    });
  }
}

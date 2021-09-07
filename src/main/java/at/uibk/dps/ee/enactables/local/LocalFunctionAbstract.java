package at.uibk.dps.ee.enactables.local;

import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import io.vertx.core.Future;

/**
 * Parent of all local function classes.
 * 
 * @author Fedor Smirnov
 */
public abstract class LocalFunctionAbstract extends FunctionAbstract {

  /**
   * Default constructor.
   * 
   * @param implId the identifier
   * @param typeId the function type
   */
  public LocalFunctionAbstract(final String implId, final String typeId, final String functionId,
      final Set<SimpleEntry<String, String>> additionalAttrs) {
    super(typeId, implId, EnactmentMode.Local.name(), functionId, additionalAttrs);
  }

  /**
   * Wrapper method to handle missing inputs.
   */
  public Future<JsonObject> processInput(final JsonObject input) {
    try {
      return processVerifiedInput(input);
    } catch (InputMissingException exc) {
      return Future.failedFuture(exc.getMessage());
    }
  }

  /**
   * Method defining the correct processing, assuming that all inputs are present.
   * 
   * @param input the json input
   * @return the future result
   */
  protected abstract Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException;

  /**
   * Reads the int input with the provided member name. Throws an exception if no
   * such member exists.
   * 
   * @param jsonInput the input of the local operation
   * @param memberName the String key for the json int element
   * @return the integer value stored with the provided key
   */
  protected int readIntInput(final JsonObject jsonInput, final String memberName)
      throws InputMissingException {
    checkInputEntry(jsonInput, memberName);
    return jsonInput.get(memberName).getAsInt();
  }

  /**
   * Reads the input object to retrieve a jsonArray/collection
   * 
   * @param jsonInput the input of the local operation
   * @param memberName the json key
   * @return the json arry
   * @throws StopException
   */
  protected JsonArray readCollectionInput(final JsonObject jsonInput, final String memberName)
      throws InputMissingException {
    checkInputEntry(jsonInput, memberName);
    try {
      return jsonInput.getAsJsonArray(memberName);
    } catch (ClassCastException exc) {
      throw new IllegalArgumentException(
          "The entry saved as " + memberName + " cannot be read as json array.", exc);
    }
  }

  /**
   * Waits for the given number of milliseconds.
   * 
   * @param milliseconds the wait time in milliseconds
   */
  protected void waitMilliseconds(final int milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException exc) {
      throw new IllegalStateException("Interrupted while sleeping.", exc);
    }
  }

  /**
   * Reads and returns the Json entry specified by the given key.
   * 
   * @param jsonInput the input of the local operation
   * @param key the given key
   * @return the Json entry specified by the given key
   * @throws StopException thrown if the entry is not found
   */
  protected JsonElement readEntry(final JsonObject jsonInput, final String key)
      throws InputMissingException {
    checkInputEntry(jsonInput, key);
    return jsonInput.get(key);
  }

  /**
   * Checks that an entry with the given key is present in the input object.
   * Throws an exception if this is not the case.
   * 
   * @param jsonInput the input of the local operation
   * @param key the key to check
   */
  protected void checkInputEntry(final JsonObject jsonInput, final String key)
      throws InputMissingException {
    if (jsonInput.get(key) == null) {
      final String message =
          "The key " + key + " is not part of the provided JsonObject for function " + functionId;
      throw new InputMissingException(message);
    }
  }
}

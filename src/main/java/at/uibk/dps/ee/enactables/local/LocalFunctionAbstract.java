package at.uibk.dps.ee.enactables.local;

import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.exception.StopException;

/**
 * Parent of all local function classes.
 * 
 * @author Fedor Smirnov
 */
public abstract class LocalFunctionAbstract implements EnactmentFunction {

  protected final String idString;
  protected final String type;

  /**
   * Default constructor.
   * 
   * @param idString the identifier
   * @param type the function type
   */
  public LocalFunctionAbstract(final String idString, final String type) {
    this.idString = idString;
    this.type = type;
  }

  @Override
  public String getId() {
    return idString;
  }

  @Override
  public String getType() {
    return type;
  }

  /**
   * Reads the int input with the provided member name. Throws an exception if no
   * such member exists.
   * 
   * @param jsonInput the input of the local operation
   * @param memberName the String key for the json int element
   * @return the integer value stored with the provided key
   */
  protected int readIntInput(final JsonObject jsonInput, final String memberName)
      throws StopException {
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
      throws StopException {
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
      throws StopException {
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
      throws StopException {
    if (jsonInput.get(key) == null) {
      final String message = "The key " + key + " is not part of the provided JsonObject";
      throw new StopException(message);
    }
  }
}

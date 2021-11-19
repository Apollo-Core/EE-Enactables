package at.uibk.dps.ee.enactables;

import java.util.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import io.vertx.core.Future;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * Parent of all functions. Implements the setting of the attributes used for
 * logging.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class FunctionAbstract implements EnactmentFunction {

  protected final Task task;
  protected final Optional<Mapping<Task, Resource>> mapping;

  /**
   * Constructor used for functions which don't have an explicit mapping (e.g.,
   * data flow or util)
   * 
   * @param task the task associated with the function
   * 
   */
  public FunctionAbstract(final Task task) {
    this.task = task;
    this.mapping = Optional.empty();
  }


  /**
   * Constructor for functions which have an explicit mapping (user functions)
   * 
   * @param task the task node
   * @param mapping the mapping of the task node
   */
  public FunctionAbstract(final Task task, final Mapping<Task, Resource> mapping) {
    this.task = task;
    this.mapping = Optional.of(mapping);
  }

  /**
   * Returns a mapping optional
   * 
   * @return a mapping optional (non empty if the function is associated with a
   *         resource)
   */
  public Optional<Mapping<Task, Resource>> getMappingOptional() {
    return this.mapping;
  }

  /**
   * Returns the task that the resource is associated with.
   * 
   * @return the task that the resource is associated with
   */
  public Task getTask() {
    return task;
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
      final String message = "The key " + key
          + " is not part of the provided JsonObject for function node" + task.getId();
      throw new InputMissingException(message);
    }
  }
}

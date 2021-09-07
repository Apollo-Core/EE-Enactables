package at.uibk.dps.ee.enactables.local.dataflow;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionDataFlowCollections;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * Enactable performing the distribution of collection data among many
 * consumers.
 * 
 * @author Fedor Smirnov
 */
public class Distribution extends LocalFunctionAbstract {

  protected final Task functionNode;

  /**
   * Default constructor.
   * 
   * @param functionNode the node modeling the distribution operation (annotated
   *        with the iteration number by this function)
   */
  public Distribution(final Task functionNode, final String idString, final String type) {
    super(idString, type, functionNode.getId(), new HashSet<>());
    this.functionNode = functionNode;
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    Optional<JsonObject> operationResult;
    if (input.size() == 1) {
      final String key = input.keySet().iterator().next();
      if (key.equals(ConstantsEEModel.JsonKeyConstantIterator)) {
        // int iterator
        operationResult = Optional.of(processIntegerIterator(input.get(key)));
      } else {
        // one collection
        operationResult = Optional.of(processSingleCollection(input.get(key), key));
      }
    } else {
      // multiple collections
      operationResult = Optional.of(processMultipleCollections(input));
    }
    return Future.succeededFuture(
        operationResult.orElseThrow(() -> new IllegalArgumentException("Incorrect Iterator")));
  }

  /**
   * Generates the json result for the case where one collection is used as
   * iterator.
   * 
   * @param element the element containing the collection (JSON array)
   * @param key the key to refer to the collection
   * @return the json result for the case where one collection is used as iterator
   */
  protected JsonObject processSingleCollection(final JsonElement element, final String key) {
    if (element.isJsonArray()) {
      final JsonObject result = new JsonObject();
      processCollection(key, element.getAsJsonArray(), result);
      PropertyServiceFunctionDataFlowCollections.setIterationNumber(functionNode,
          element.getAsJsonArray().size());
      return result;
    } else {
      throw new IllegalArgumentException("Incorrect iterator.");
    }
  }

  /**
   * Generates the json result for the case where multiple collections are used as
   * iterators.
   * 
   * @param input the input json object
   * @return the json result for the case where multiple collections are used as
   *         iterators
   */
  protected JsonObject processMultipleCollections(final JsonObject input) {
    int collSize = -1;
    final JsonObject result = new JsonObject();
    for (final Entry<String, JsonElement> entry : input.entrySet()) {
      final JsonElement element = entry.getValue();
      if (!element.isJsonArray()) {
        throw new IllegalArgumentException("Multiple iterators, not all are collections.");
      }
      final JsonArray array = element.getAsJsonArray();
      if (collSize == -1) {
        collSize = array.size();
      }
      if (collSize != array.size()) {
        throw new IllegalStateException("Collections of different size used as iterators.");
      }
      final String key = entry.getKey();
      processCollection(key, array, result);
    }
    if (collSize == -1) {
      throw new IllegalStateException("Empty json input distribution");
    }
    PropertyServiceFunctionDataFlowCollections.setIterationNumber(functionNode, collSize);
    return result;
  }

  /**
   * Processes the collection by creating an entry for each element
   * 
   * @param key the key of the collection
   * @param array the array
   * @param the object which will be the Json result
   */
  protected void processCollection(final String key, final JsonArray array,
      final JsonObject jsonObject) {
    final List<String> elementKeyList = Stream.iterate(0, i -> i + 1).limit(array.size())
        .map(i -> ConstantsEEModel.getCollectionElementKey(key, i)).collect(Collectors.toList());
    elementKeyList.forEach(
        elementKey -> jsonObject.add(elementKey, array.get(elementKeyList.indexOf(elementKey))));
  }

  /**
   * Creates a collection representing iterations with the provided number.
   * 
   * @param iterationNum
   */
  protected JsonObject processIntegerIterator(final JsonElement element) {
    if (element.isJsonPrimitive()) {
      final JsonPrimitive primitive = element.getAsJsonPrimitive();
      if (primitive.isNumber()) {
        final int iterationNum = primitive.getAsInt();
        PropertyServiceFunctionDataFlowCollections.setIterationNumber(functionNode, iterationNum);
        final JsonObject result = new JsonObject();
        final List<String> keyList = Stream.iterate(0, i -> i + 1).limit(iterationNum)
            .map(i -> ConstantsEEModel
                .getCollectionElementKey(ConstantsEEModel.JsonKeyConstantIterator, i))
            .collect(Collectors.toList());
        keyList.forEach(key -> result.add(key, new JsonPrimitive(true)));
        return result;
      }
    }
    throw new IllegalArgumentException("Incorrect int iterator.");
  }
}

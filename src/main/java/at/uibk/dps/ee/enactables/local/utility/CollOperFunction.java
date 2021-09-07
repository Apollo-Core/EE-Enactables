package at.uibk.dps.ee.enactables.local.utility;

import java.util.HashSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.enactables.local.InputMissingException;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import io.vertx.core.Future;
import net.sf.opendse.model.Task;

/**
 * The {@link CollOperFunction} is a utility enactable responsible for the
 * transformation of collections.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperFunction extends LocalFunctionAbstract {

  protected final String subCollectionString;
  protected final CollectionOperation collectionOperation;

  /**
   * Standard constructor
   * 
   * @param functionNode the function node modeling the collection operation
   */
  public CollOperFunction(final Task functionNode, final String idString, final String type) {
    super(idString, type, functionNode.getId(), new HashSet<>());
    this.subCollectionString =
        PropertyServiceFunctionUtilityCollections.getSubCollectionsString(functionNode);
    this.collectionOperation =
        PropertyServiceFunctionUtilityCollections.getCollectionOperation(functionNode);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input)
      throws InputMissingException {
    // get the collection
    final String collectionKey = UtilsCollections.getCollectionKey(input)
        .orElseThrow(() -> new IllegalArgumentException("Key for collection not found."));
    final JsonArray jsonArray = input.get(collectionKey).getAsJsonArray();
    final CollOper subCollectionOperation = getSubCollectionOperation(input);
    final JsonElement resultElement = subCollectionOperation.transformCollection(jsonArray);
    final JsonObject jsonResult = new JsonObject();
    jsonResult.add(collectionKey, resultElement);
    return Future.succeededFuture(jsonResult);
  }

  /**
   * Returns the {@link CollOper} applicable for the configures collection
   * operation
   * 
   * @param jsonInput the input object of the function task
   * 
   * @return the {@link CollOper} applicable for the configures collection
   *         operation
   */
  protected CollOper getSubCollectionOperation(final JsonObject jsonInput) {
    switch (collectionOperation) {
      case ElementIndex:
        return UtilsCollections.readSubCollections(subCollectionString, jsonInput);

      case Block:
        return new CollOperBlock(subCollectionString, jsonInput);

      case Replicate:
        return new CollOperReplicate(subCollectionString, jsonInput);

      case Split:
        return new CollOperSplit(subCollectionString, jsonInput);

      default:
        throw new IllegalStateException(
            "No subcollections known for operation " + collectionOperation.name());
    }
  }
}

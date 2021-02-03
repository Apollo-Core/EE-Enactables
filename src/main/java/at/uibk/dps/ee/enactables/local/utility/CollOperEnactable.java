package at.uibk.dps.ee.enactables.local.utility;

import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.local.LocalAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCollections.CollectionOperation;
import net.sf.opendse.model.Task;

/**
 * The {@link CollOperEnactable} is a utility enactable responsible for the
 * transformation of collections.
 * 
 * @author Fedor Smirnov
 *
 */
public class CollOperEnactable extends LocalAbstract {

	protected final String subCollectionString;
	protected final CollectionOperation collectionOperation;

	/**
	 * Constructor with the functionality of the parent and setting the
	 * collOperation parameters
	 */
	protected CollOperEnactable(final Set<EnactableStateListener> stateListeners, final Task functionNode) {
		super(stateListeners, functionNode);
		this.subCollectionString = PropertyServiceFunctionUtilityCollections.getSubCollectionsString(functionNode);
		this.collectionOperation = PropertyServiceFunctionUtilityCollections.getCollectionOperation(functionNode);
	}

	@Override
	protected void myPlay() throws StopException {
		// get the collection
		final String collectionKey = UtilsCollections.getCollectionKey(jsonInput)
				.orElseThrow(() -> new IllegalArgumentException("Key for collection not found."));
		final JsonArray jsonArray = jsonInput.get(collectionKey).getAsJsonArray();
		final CollOper subCollectionOperation = getSubCollectionOperation();
		final JsonElement resultElement = subCollectionOperation.transformCollection(jsonArray);
		jsonResult = new JsonObject();
		jsonResult.add(collectionKey, resultElement);
	}

	@Override
	protected void myPause() {
		// no special behavior here
	}

	/**
	 * Returns the {@link CollOper} applicable for the configures collection
	 * operation
	 * 
	 * @return the {@link CollOper} applicable for the configures collection
	 *         operation
	 */
	protected CollOper getSubCollectionOperation() {
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
			throw new IllegalStateException("No subcollections known for operation " + collectionOperation.name());
		}
	}
}

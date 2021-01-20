package at.uibk.dps.ee.enactables.local.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Used for the collection transformation which corresponds to the SPLIT
 * constraint of the AFCL language.
 * 
 * @author Fedor Smirnov
 */
public class CollOperSplit implements CollOper {

	protected final int splitNumber;

	/**
	 * Standard constructor.
	 * 
	 * @param collOperString the string configuring the operation
	 * @param jsonInput      the json input (potentially containing the dynamic
	 *                       config parameters)
	 */
	public CollOperSplit(final String collOperString, final JsonObject jsonInput) {
		this.splitNumber = getSplitNumber(collOperString, jsonInput);
	}

	@Override
	public JsonElement transformCollection(JsonArray originalCollection) {
		JsonArray result = new JsonArray();
		int elementsPerResultEntry = (int) Math.round((Math.ceil(1. * originalCollection.size() / splitNumber)));

		for (int i = 0; i < splitNumber; i++) {
			int start = i * elementsPerResultEntry;
			int end = Math.min(originalCollection.size(), (i + 1) * elementsPerResultEntry);
			JsonArray entry = new JsonArray();
			for (int ii = start; ii < end; ii++) {
				entry.add(originalCollection.get(ii));
			}
			result.add(entry);
		}
		return result;
	}

	/**
	 * Reads the replication number from the passed string (possibly using the json
	 * input)
	 * 
	 * @param collOperString the string describing the replicate operation
	 * @param jsonInput      the json input of the enactable
	 * @return the replication number
	 */
	protected final int getSplitNumber(final String collOperString, final JsonObject jsonInput) {
		int result = UtilsCollections.determineCollOperParam(collOperString, jsonInput);
		if (result < 2) {
			throw new IllegalArgumentException("The split number must be bigger than 1.");
		}
		return result;
	}

}

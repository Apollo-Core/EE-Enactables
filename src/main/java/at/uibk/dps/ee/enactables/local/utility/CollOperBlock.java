package at.uibk.dps.ee.enactables.local.utility;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;

/**
 * Utility function to transform a collection following the BLOCK operators as
 * defined in the AFCL language.
 * 
 * @author Fedor Smirnov
 */
public class CollOperBlock implements CollOper {

	protected final int size;
	protected final int overlap;

	/**
	 * Standard constructor
	 * 
	 * @param subCollectionString the string defining the parameters of the block
	 *                            operation
	 * @param jsonInput           the JsonInput of the enactable (possibly storing
	 *                            the dynamic parameters)
	 */
	public CollOperBlock(final String subCollectionString, final JsonObject jsonInput) {
		this.size = getSize(subCollectionString, jsonInput);
		this.overlap = getOverlap(subCollectionString, jsonInput);
		if (overlap >= size) {
			throw new IllegalArgumentException("Overlap has to be smaller than size.");
		}
	}

	@Override
	public JsonElement transformCollection(final JsonArray originalCollection) {
		if (size == 1 && overlap == 0) {
			// do nothing for (1, 0)
			return originalCollection;
		} else {
			final JsonArray result = new JsonArray();
			final List<JsonArray> entries = Stream.generate(() -> new JsonArray())
					.limit(getResultEntryNumber(originalCollection.size(), size, overlap)).collect(Collectors.toList());
			int entryIdx = 0;
			for (int start = 0; start - overlap + size < originalCollection.size(); start += size - overlap) {
				final JsonArray entry = entries.get(entryIdx++);
				for (int idx = start; idx < start + size; idx++) {
					entry.add(originalCollection.get(idx));
				}
				result.add(entry);
			}
			return result;
		}
	}

	/**
	 * Returns the number of entries that the application of the block operator will
	 * result in.
	 * 
	 * @param originalSize
	 * @param blockSize
	 * @param overlap
	 * @return
	 */
	protected int getResultEntryNumber(int originalSize, final int blockSize, final int overlap) {
		originalSize -= blockSize;
		final int advance = blockSize - overlap;
		return (int) Math.floor(originalSize / advance) + 1;
	}

	/***
	 * Retrieves the block size from the given string.
	 * 
	 * @param subcollectionString the given string.
	 * @param jsonInput           the json input
	 * @return the block size
	 */
	protected final int getSize(final String subcollectionString, final JsonObject jsonInput) {
		final int result = UtilsCollections
				.determineCollOperParam(subcollectionString.split(ConstantsEEModel.BlockSeparator)[0], jsonInput);
		if (result < 1) {
			throw new IllegalArgumentException("Block size must be greater equal 1.");
		}
		return result;
	}

	/***
	 * Retrieves the overlap from the given string.
	 * 
	 * @param subcollectionString the given string.
	 * @param jsonInput           the json input
	 * @return the overlap
	 */
	protected final int getOverlap(final String subcollectionString, final JsonObject jsonInput) {
		final int result = UtilsCollections
				.determineCollOperParam(subcollectionString.split(ConstantsEEModel.BlockSeparator)[1], jsonInput);
		if (result < 0) {
			throw new IllegalArgumentException("Overlap must be non-negative.");
		}
		return result;
	}
}

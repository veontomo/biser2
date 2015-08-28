package com.veontomo.bead.api;

import com.veontomo.bead.Storage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Performs bead-related search in database
 */
public class BeadSearcher {
    private final Storage mStorage;

    public BeadSearcher(final Storage storage) {
        this.mStorage = storage;
    }

    /**
     * Fills in the adapter with bead color codes that are similar to given one.
     * @param code  color codes similar to this one should be found
     * @param adapter to whom handle the result
     */
    public void fillInWithSimilar(String code, SimilarBeadAdapter adapter) {
        adapter.prepend(new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"})));

    }
}

package com.veontomo.bead.api;

import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.SimilarBeadFinderTask;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Performs bead-related search in database
 */
public class BeadSearcher {
    private final Storage storage;

    private String[] items;

    private SimilarBeadFinderTask worker;

    public BeadSearcher(final Storage storage) {
        this.storage = storage;
    }

    /**
     * Fills in the adapter with bead color codes that are similar to given one.
     * @param code  color codes similar to this one should be found
     * @param adapter to whom handle the result
     */
    public void fillInWithSimilar(String code, SimilarBeadAdapter adapter) {
        this.worker = new SimilarBeadFinderTask(storage, adapter, items);
        this.worker.execute(code);
        //adapter.prepend(new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"})));

    }
}

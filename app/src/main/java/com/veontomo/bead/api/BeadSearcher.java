package com.veontomo.bead.api;

import android.util.Log;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.SimilarBeadFinderTask;

/**
 * Performs bead-related search in database
 */
public class BeadSearcher {
    private final Storage storage;

    private String[] items;

    private SimilarBeadFinderTask worker;
    /**
     * cached array of all color codes
     */
    public String[] data;

    public BeadSearcher(final Storage storage) {
        this.storage = storage;
    }

    /**
     * Fills in the adapter with bead color codes that are similar to given one.
     * @param code  color codes similar to this one should be found
     * @param adapter to whom handle the result
     */
    public void fillInWithSimilar(String code, SimilarBeadAdapter adapter) {
        this.worker = new SimilarBeadFinderTask(storage, adapter, this);
        this.worker.execute(code);
        //adapter.prepend(new ArrayList<>(Arrays.asList(new String[]{"a", "b", "c"})));

    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data){
        this.data = data;
    }
}

package com.veontomo.bead.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.SimilarBeadFinderTask;
import com.veontomo.bead.bktree.BKTree;
import com.veontomo.bead.bktree.Distance;
import com.veontomo.bead.bktree.Levenshtein;

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
    private BKTree<String> tree;

    /**
     * implementation of distance between two strings
     */
    private Distance<String> distance;

    public BeadSearcher(final Storage storage) {
        this.storage = storage;
    }

    /**
     * Fills in the adapter with bead color codes that are similar to given one.
     *
     * @param code    color codes similar to this one should be found
     * @param adapter to whom handle the result
     */
    public void fillInWithSimilar(String code, SimilarBeadAdapter adapter) {
        this.worker = new SimilarBeadFinderTask(storage, adapter, this);
        this.worker.execute(code);
    }

    /**
     * {@link #tree tree} getter
     * @return
     */
    public BKTree<String> getTree() {
        return tree;
    }

    /**
     * Builds the BKTree from array of strings.
     * <p></p>
     * The first element of the given array becomes the root of the tree.
     *
     * @param data
     */
    public void buildTree(@NonNull final String[] data) {
        distance = new Levenshtein();
        int size = data.length;
        Log.i(Config.TAG, "constructing tree for " + size + " elements");
        if (size > 0) {
            // use the first element of the array as the root
            tree = new BKTree<>(distance, data[0]);
        }
        // add the other elements of the array
        for (int i = 1; i < size; i++) {
            Log.i(Config.TAG, "inserting " + data[i]);
            tree.insert(data[i]);
        }
    }
}

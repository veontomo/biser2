package com.veontomo.bead.api;

import android.support.annotation.NonNull;
import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.SimilarBeadFinderTask;
import com.veontomo.bead.bktree.BKTree;
import com.veontomo.bead.bktree.Distance;
import com.veontomo.bead.bktree.Levenshtein;

/**
 * Performs bead-related search in database.
 *
 * Internally, this class uses an async task to perform search.
 */
public class BeadSearcher {
    private final Storage storage;

    /**
     * cached array of all color codes
     */
    private static BKTree<String> tree;
    /**
     * distance function
     */
    public final Distance<String> distance;

    public BeadSearcher(final Storage storage) {
        this.storage = storage;
        this.distance = new Levenshtein();
    }

    /**
     * Fills in the adapter with bead color codes that are similar to given one.
     *
     * @param code    color codes similar to this one should be found
     * @param adapter to whom handle the result
     */
    public void fillInWithSimilar(String code, SimilarBeadAdapter adapter) {
        SimilarBeadFinderTask worker = new SimilarBeadFinderTask(storage, adapter, this);
        worker.execute(code);
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

        int size = data.length;
        if (size > 0) {
            // use the first element of the array as the root
            tree = new BKTree<>(distance, data[0]);
        }
        // add the other elements of the array
        for (int i = 1; i < size; i++) {
            tree.insert(data[i]);
        }
    }
}

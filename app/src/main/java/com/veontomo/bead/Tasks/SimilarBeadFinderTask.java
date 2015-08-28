package com.veontomo.bead.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.BKTree;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.api.Distance;
import com.veontomo.bead.api.SimilarBeadAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, Set<String>> {
    private final SimilarBeadAdapter mAdapter;
    private final Storage storage;
    private final BeadSearcher searcher;
    Distance<String> distance = new Distance<String>() {
        @Override
        public double eval(String rhs, String lhs) {
            // Create matrix of rhs.size+1 * lhs.size+1
            int[][] matrix = new int[rhs.length() + 1][lhs.length() + 1];

            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = i;
            }

            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = j;
            }

            for (int i = 1; i < matrix.length; i++) {

                for (int j = 1; j < matrix[0].length; j++) {

                    int remove = matrix[i - 1][j] + 1;
                    int add = matrix[i][j - 1] + 1;
                    int sub = matrix[i - 1][j - 1] + (rhs.charAt(i - 1) == lhs.charAt(j - 1) ? 0 : 1);

                    matrix[i][j] = Math.min(remove, Math.min(add, sub));

                }

            }


            return matrix[rhs.length()][lhs.length()];

        }
    };

    public SimilarBeadFinderTask(Storage storage, SimilarBeadAdapter adapter, BeadSearcher searcher) {
        Log.i(Config.TAG, "new  SimilarBeadFinderTask task");
        this.storage = storage;
        this.mAdapter = adapter;
        this.searcher = searcher;
    }

    @Override
    protected Set<String> doInBackground(String... params) {
        if (searcher.getData() == null) {
            Log.i(Config.TAG, "data is null");
            searcher.setData(storage.getColorCodes());
            Log.i(Config.TAG, "data now has  " + searcher.getData().length + " elements");
        } else {
            Log.i(Config.TAG, "data is NOT null");
        }
        BKTree<String> tree = initialize(searcher.getData());
        Log.i(Config.TAG, "tree contains " + tree.numOfChildren() + ", pivot: " + params[0]);
        return tree.searchWithin(params[0], 2d);
    }

    private BKTree<String> initialize(String[] data) {
        BKTree<String> tree = new BKTree<>(distance, "---");
        for (String word : data) {
            Log.i(Config.TAG, "inserting word " + word);
            tree.insert(word);
        }
        return tree;
    }

    @Override
    protected void onPostExecute(Set<String> items) {
        List<String> list = new ArrayList<>();
        list.addAll(items);
        mAdapter.setItems(list);
    }


}

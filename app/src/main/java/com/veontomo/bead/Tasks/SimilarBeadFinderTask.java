package com.veontomo.bead.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.bktree.BKTree;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.bktree.Distance;
import com.veontomo.bead.api.SimilarBeadAdapter;
import com.veontomo.bead.bktree.Levenshtein;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, Set<String>> {
    private final SimilarBeadAdapter mAdapter;
    private final Storage storage;
    private final BeadSearcher searcher;


    public SimilarBeadFinderTask(Storage storage, SimilarBeadAdapter adapter, BeadSearcher searcher) {
        Log.i(Config.TAG, "new  SimilarBeadFinderTask task");
        this.storage = storage;
        this.mAdapter = adapter;
        this.searcher = searcher;
    }

    @Override
    protected Set<String> doInBackground(String... params) {
        if (searcher.getTree() == null) {
            Log.i(Config.TAG, "tree is null");
            searcher.buildTree(storage.getColorCodes());
            Log.i(Config.TAG, "tree is built");
        } else {
            Log.i(Config.TAG, "tree is NOT null");
        }
        return searcher.getTree().searchWithin(params[0], 2d);
    }



    @Override
    protected void onPostExecute(Set<String> items) {
        List<String> list = new ArrayList<>();
        list.addAll(items);
        mAdapter.setItems(list);
    }


}

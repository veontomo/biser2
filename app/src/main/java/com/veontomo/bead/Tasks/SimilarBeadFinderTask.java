package com.veontomo.bead.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.api.SimilarBeadAdapter;
import com.veontomo.bead.bktree.Distance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, LinkedList<String>> {
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
    protected LinkedList<String> doInBackground(String... params) {
        LinkedList<String> result = null;
        Set<String> result2 = null;
        if (params.length == 0) {
            return null;
        }
        // consider first element only
        String seed = params[0];
        if (searcher.getTree() == null) {
            searcher.buildTree(storage.getColorCodes());
        }
        result = new LinkedList(searcher.getTree().searchAt(seed, 1d));
        result2 = searcher.getTree().searchAt(seed, 2d);
        result.addAll(result2);
        return result;

    }


    @Override
    protected void onPostExecute(LinkedList<String> items) {
        List<String> list = new ArrayList<>();
        list.addAll(items);
        mAdapter.setItems(list);
    }


}

package com.veontomo.bead.Tasks;

import android.os.AsyncTask;

import com.veontomo.bead.Storage;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.api.SimilarBeadAdapter;

import java.util.Set;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, String[]> {
    private final SimilarBeadAdapter mAdapter;
    private final Storage storage;
    private final BeadSearcher searcher;


    public SimilarBeadFinderTask(Storage storage, SimilarBeadAdapter adapter, BeadSearcher searcher) {
        //Log.i(Config.TAG, "new  SimilarBeadFinderTask task");
        this.storage = storage;
        this.mAdapter = adapter;
        this.searcher = searcher;
    }

    @Override
    protected String[] doInBackground(String... params) {
        Set<String> dist1;
        Set<String> dist2;
        if (params.length == 0) {
            return null;
        }
        // consider first element only
        String seed = params[0];
        if (searcher.getTree() == null) {
            searcher.buildTree(storage.getColorCodes());
        }
        dist1 = searcher.getTree().searchAt(seed, 1d);
        dist2 = searcher.getTree().searchAt(seed, 2d);
        int i = 0;
        String[] result = new String[dist1.size() + dist2.size()];
        for (String str : dist1) {
            result[i] = str;
            i++;
        }
        for (String str : dist2) {
            result[i] = str;
            i++;
        }
        return result;

    }


    @Override
    protected void onPostExecute(final String[] items) {
        mAdapter.setItems(items);
    }


}

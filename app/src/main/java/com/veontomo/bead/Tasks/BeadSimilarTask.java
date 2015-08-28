package com.veontomo.bead.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;


import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Async task to find beads similar to a given one.
 */
public class BeadSimilarTask extends AsyncTask<String, Void, String[]> {
    private final Storage storage;
    private final ArrayAdapter<String> mAdapter;

    /**
     * Constructor.
     * @param storage where to perform the search
     * @param adapter to whom the result of the search should be handled
     */
    public BeadSimilarTask(Storage storage, ArrayAdapter<String> adapter){
        this.storage = storage;
        this.mAdapter = adapter;
    }
    @Override
    protected String[] doInBackground(String... params) {
        Log.i(Config.TAG, "executing " + params[0]);
        return params;
    }

    @Override
    protected void onPostExecute(String[] items){
//        List<String> list = new ArrayList<>();
//        list.add("aaa");
//        list.add("bbb");
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }
}

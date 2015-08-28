package com.veontomo.biser2.Tasks;

import android.os.AsyncTask;
import com.veontomo.biser2.api.SimilarBeadAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, String[]>{
    private final SimilarBeadAdapter mAdapter;

    public SimilarBeadFinderTask(SimilarBeadAdapter adapter){
        this.mAdapter = adapter;
    }
    @Override
    protected String[] doInBackground(String... params) {
        return params;
    }

    @Override
    protected void onPostExecute(String[] items){
        mAdapter.prepend(new ArrayList<>(Arrays.asList(items)));
    }
}

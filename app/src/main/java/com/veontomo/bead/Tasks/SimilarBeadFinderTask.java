package com.veontomo.bead.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.SimilarBeadAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Performs search of similar beads
 */
public class SimilarBeadFinderTask extends AsyncTask<String, Void, String[]>{
    private final SimilarBeadAdapter mAdapter;
    private final Storage storage;
    private String[] data;

    public SimilarBeadFinderTask(Storage storage, SimilarBeadAdapter adapter, String[] data){
        Log.i(Config.TAG, "new  SimilarBeadFinderTask task");
        this.storage = storage;
        this.mAdapter = adapter;
        this.data = data;
    }
    @Override
    protected String[] doInBackground(String... params) {
        if (this.data == null){
            Log.i(Config.TAG, "data is null");
            this.data = storage.getColorCodes();
            Log.i(Config.TAG, "data now has  " + this.data.length + " elements");
        } else {
            Log.i(Config.TAG, "data is NOT null");
        }
        return params;
    }

    @Override
    protected void onPostExecute(String[] items){
        mAdapter.prepend(new ArrayList<>(Arrays.asList(this.data)));
    }
}

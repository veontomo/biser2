package com.veontomo.biser2.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.veontomo.biser2.Storage;
import com.veontomo.biser2.api.Bead;
import com.veontomo.biser2.api.BeadAdapter;
import com.veontomo.biser2.api.Location;

import java.util.List;

/**
 * Finds bead with given color code in database
 */
public class BeadFinderTask extends AsyncTask<String, Void, Void> {
    private final Storage storage;
    /**
     * adapter that should take care of result of the search
     */
    private final BeadAdapter mCallback;

    private Bead searchResult;

    /**
     * Constructor.
     * @param storage where to perform the search
     * @param callback to whom the result of the search should be handled
     */
    public BeadFinderTask(Storage storage, BeadAdapter callback){
        this.storage = storage;
        this.mCallback = callback;
    }
    @Override
    protected Void doInBackground(String... params) {
        this.searchResult = this.storage.beadByColor(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void arg){
        mCallback.prependItem(this.searchResult );
    }
}

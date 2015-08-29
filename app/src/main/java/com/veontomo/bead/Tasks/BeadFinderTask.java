package com.veontomo.bead.Tasks;

import android.os.AsyncTask;

import com.veontomo.bead.Fragments.SearchAndHistoryFragment;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.BeadAdapter;

/**
 * Finds bead with given color code in database
 */
public class BeadFinderTask extends AsyncTask<String, Void, Void> {
    private final Storage storage;
    /**
     * adapter that should take care of result of the search
     */
    private final BeadAdapter mCallback;
    private final SearchAndHistoryFragment.OnBeadSearchListener handler;

    private Bead searchResult;

    /**
     * Constructor.
     * @param storage where to perform the search
     * @param adapter to whom the result of the search should be handled
     */
    public BeadFinderTask(Storage storage, BeadAdapter adapter, SearchAndHistoryFragment.OnBeadSearchListener handler){
        this.storage = storage;
        this.mCallback = adapter;
        this.handler = handler;
    }
    @Override
    protected Void doInBackground(String... params) {
        this.searchResult = this.storage.beadByColor(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void arg){
        if (this.searchResult.loc == null){
            this.handler.onColorCodeAbsent(this.searchResult.colorCode);
        }
        mCallback.prependItem(this.searchResult );
    }
}

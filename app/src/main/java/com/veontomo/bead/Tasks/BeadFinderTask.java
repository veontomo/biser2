package com.veontomo.bead.Tasks;

import android.os.AsyncTask;

import com.veontomo.bead.Fragments.SearchAndHistoryFragment;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.BeadAdapter;

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
    private final SearchAndHistoryFragment.OnBeadSearchListener handler;

    private List<Bead> searchResult;

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
        this.searchResult = this.storage.beadByColors(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void arg){
        if (this.searchResult.size() == 1 && this.searchResult.get(0).loc == null){
            this.handler.onColorCodeAbsent(this.searchResult.get(0).colorCode);
        }
        mCallback.prependItems(this.searchResult);
    }
}

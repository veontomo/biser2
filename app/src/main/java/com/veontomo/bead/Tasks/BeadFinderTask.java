package com.veontomo.bead.Tasks;

import android.os.AsyncTask;

import com.veontomo.bead.Fragments.SearchAndHistoryFragment;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.BeadAdapter;
import com.veontomo.bead.api.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Finds bead with given color code in database
 */
public class BeadFinderTask extends AsyncTask<String, Void, Void> {
    private final Storage storage;
    /**
     * adapter that should take care of result of the search
     */
    private final BeadAdapter mAdapter;
    private final SearchAndHistoryFragment.OnBeadSearchListener handler;

    private List<Bead> searchResult;

    /**
     * Constructor.
     * @param storage where to perform the search
     * @param adapter to whom the result of the search should be handled
     */
    public BeadFinderTask(Storage storage, BeadAdapter adapter, SearchAndHistoryFragment.OnBeadSearchListener handler){
        this.storage = storage;
        this.mAdapter = adapter;
        this.handler = handler;
    }

    @Override
    protected Void doInBackground(String... params) {
        HashMap<String, Location> locations = this.storage.locationByColors(params);
        this.searchResult = new ArrayList<>();
        for (String code : params){
            if (locations.containsKey(code)){
                this.searchResult.add(new Bead(code, locations.get(code)));
            } else {
                this.searchResult.add(new Bead(code, null));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void arg){
        if (this.searchResult.size() == 1 && this.searchResult.get(0).loc == null){
            this.handler.onColorCodeAbsent(this.searchResult.get(0).colorCode);
        }
        mAdapter.prependItems(this.searchResult);
    }
}

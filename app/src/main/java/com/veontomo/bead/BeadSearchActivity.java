package com.veontomo.bead;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.bead.Fragments.SearchAndHistoryFragment;
import com.veontomo.bead.Fragments.SimilarBeadFragment;
import com.veontomo.bead.Tasks.BeadLoaderTask;

public class BeadSearchActivity extends Activity implements SearchAndHistoryFragment.OnFragmentInteractionListener {

    private final String marker = "activity: ";

    /**
     * fragment that displays similar bead colors
     */
    private SimilarBeadFragment mSimilarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bead_search);
        // BeadLoaderTask loader = new BeadLoaderTask(getApplicationContext());
        // loader.execute("locations.txt");
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedBundle) {
        super.onRestoreInstanceState(savedBundle);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mSimilarFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.similar);
        Log.i(Config.TAG, "two pane mode? " + (mSimilarFragment != null));
        if (getResources().getBoolean(R.bool.dual_pane)) {
            Log.i(Config.TAG, "two pane mode");
        } else {
            Log.i(Config.TAG, "single pane mode");
        }
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mSimilarFragment = null;
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onStop() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bead_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void acceptSearchTerm(String str) {
        if (getResources().getBoolean(R.bool.dual_pane)) {
            this.mSimilarFragment.updateView(str);
        }
    }


}

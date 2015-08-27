package com.veontomo.biser2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.biser2.Fragments.SearchAndHistoryFragment;
import com.veontomo.biser2.Fragments.SimilarBeadFragment;
import com.veontomo.biser2.Tasks.BeadLoaderTask;

public class BeadSearchActivity extends Activity implements SearchAndHistoryFragment.OnFragmentInteractionListener {

    private String marker = "activity: ";

    /**
     * fragment that displays similar bead colors
     */
    private SimilarBeadFragment mSimilarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bead_search);
        BeadLoaderTask loader = new BeadLoaderTask(getApplicationContext());
        loader.execute("locations.txt");
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mSimilarFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.similar);
        Log.i(Config.TAG, "two pane mode? " + (mSimilarFragment != null));

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedBundle) {
        super.onRestoreInstanceState(savedBundle);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
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
        if (this.mSimilarFragment != null){
            this.mSimilarFragment.updateView(str);
        }
    }


}

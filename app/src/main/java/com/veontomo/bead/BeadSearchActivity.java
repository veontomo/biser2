package com.veontomo.bead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.bead.Fragments.SearchAndHistoryFragment;
import com.veontomo.bead.Fragments.SimilarBeadFragment;

public class BeadSearchActivity extends Activity implements SearchAndHistoryFragment.OnBeadSearchListener, SimilarBeadFragment.SimilarBeadListener {

    private final String marker = "bead search activity: ";

    /**
     * fragment that displays similar bead colors
     */
    private SimilarBeadFragment mSimilarFragment;

    /**
     * fragment that performs search of bead colors
     */
    private SearchAndHistoryFragment mSearchFragment;

    /**
     * a token to identify result from a start-for-result action
     */
    private static final int SIMILAR_BEAD_REQUEST = 4;

    /**
     * a string received from a start-for-result action
     */
    private String onResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bead_search);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mSimilarFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.similar);
        this.mSearchFragment = (SearchAndHistoryFragment) getFragmentManager().findFragmentById(R.id.search);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedBundle) {
        super.onRestoreInstanceState(savedBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (this.onResult != null) {
            this.mSearchFragment.insert(this.onResult);
        }
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        this.mSearchFragment = null;
        this.mSimilarFragment = null;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SIMILAR_BEAD_REQUEST) {
            String code = data.getStringExtra(getString(R.string.EXTRA_SEARCH_INITIAL));
            if (code != null) {
                this.onResult = code;
            }
        }
    }

    @Override
    public void onColorCodeReceived(String str) {
//        if (getResources().getBoolean(R.bool.dual_pane)) {
//            this.mSimilarFragment.updateView(str);
//        }
    }

    @Override
    public void onColorCodeAbsent(String str) {
        if (getResources().getBoolean(R.bool.dual_pane)) {
            this.mSimilarFragment.updateView(str);
        } else {
            Intent intent = new Intent(getApplicationContext(), SimilarBeadActivity.class);
            intent.putExtra(SimilarBeadActivity.COLOR_CODE_KEY, str);
            startActivityForResult(intent, SIMILAR_BEAD_REQUEST);
        }
    }

    @Override
    public void onColorCodeClick(String str) {
        if (getResources().getBoolean(R.bool.dual_pane)) {
            this.mSimilarFragment.updateView(str);
        }
    }


    @Override
    public void initialize(String str) {
        /// TODO
    }

    @Override
    public void onSimilarColorCodeClick(String str) {
        if (getResources().getBoolean(R.bool.dual_pane)) {
            this.mSearchFragment.insert(str);
        }
    }
}

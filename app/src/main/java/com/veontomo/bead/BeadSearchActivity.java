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

public class BeadSearchActivity extends Activity implements SearchAndHistoryFragment.OnBeadSearchListener {

    private final String marker = "activity: ";

    /**
     * fragment that displays similar bead colors
     */
    private SimilarBeadFragment mSimilarFragment;

    /**
     * last entered search term
     */
    private String searchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bead_search);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedBundle) {
        super.onRestoreInstanceState(savedBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mSimilarFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.similar);
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
    public void onColorCodeReceived(String str) {
        this.searchTerm = str;
        if (getResources().getBoolean(R.bool.dual_pane)) {
            this.mSimilarFragment.updateView(str);
        }
    }

    @Override
    public void onColorCodeAbsent(String str) {
        Intent intent = new Intent(getApplicationContext(), SimilarBeadActivity.class);
        intent.putExtra("color", str);
        startActivity(intent);
    }


}

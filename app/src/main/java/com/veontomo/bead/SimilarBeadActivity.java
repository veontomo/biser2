package com.veontomo.bead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.bead.Fragments.SimilarBeadFragment;

public class SimilarBeadActivity extends Activity implements SimilarBeadFragment.SimilarBeadListener {

    private final String marker = "similar bead activity: ";
    private SimilarBeadFragment mFragment;
    /**
     * name of the key under which the {@link #mFragment fragment} is stored in the bundle
     */
    private static final String FRAGMENT_KEY = "fragmentKey";

    /**
     * a token under which an intent passed to this activity stores a color code that is used as an
     * initial parameter
     */
    public static final String COLOR_CODE_KEY = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_bead);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (savedInstanceState != null) {
            Log.i(Config.TAG, this.marker + " restore fragment");
            mFragment = (SimilarBeadFragment) getFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
        }
        Intent intent = getIntent();
        if (intent != null) {
            String code = intent.getStringExtra(COLOR_CODE_KEY);
            if (code != null) {
                initialize(code);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        getFragmentManager().putFragment(outState, FRAGMENT_KEY, mFragment);
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onPause();
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
        getMenuInflater().inflate(R.menu.menu_similar_bead, menu);
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
    public void initialize(String str) {
        mFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.activity_similar);
        mFragment.setCode(str);
    }

    @Override
    public void onSimilarColorCodeClick(String str) {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.EXTRA_SEARCH_INITIAL), str);
        setResult(RESULT_OK, intent);
        finish();
    }
}

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_bead);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        Intent intent = getIntent();
        if (intent != null) {
            String code = intent.getStringExtra("color");
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
        SimilarBeadFragment ft = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.activity_similar);
        ft.setCode(str);
    }
}

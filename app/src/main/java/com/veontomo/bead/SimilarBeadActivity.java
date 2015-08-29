package com.veontomo.bead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.veontomo.bead.Fragments.SimilarBeadFragment;
import com.veontomo.bead.R;

public class SimilarBeadActivity extends AppCompatActivity implements SimilarBeadFragment.SimilarBeadListener {

    private SimilarBeadFragment mSimilarFragment;
    private String colorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_bead);
        Intent intent = getIntent();
        if (intent != null) {
            String code = intent.getStringExtra("color");

            if (code != null) {
                this.colorCode = code;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mSimilarFragment = (SimilarBeadFragment) getFragmentManager().findFragmentById(R.id.activity_similar);
        this.mSimilarFragment.updateView(this.colorCode);
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
    public void sendColor(String str) {

    }
}

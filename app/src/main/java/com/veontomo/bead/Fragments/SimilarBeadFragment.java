package com.veontomo.bead.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.veontomo.bead.Config;
import com.veontomo.bead.R;
import com.veontomo.bead.Storage;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.api.SimilarBeadAdapter;

import java.util.ArrayList;


/**
 *
 */
public class SimilarBeadFragment extends Fragment {
    private final String marker = "similar bead fragment: ";

    /**
     * color codes similar to this one are supposed to be displayed by the fragment
     */
    private String colorCode;
    /**
     * name of the key under which {@link #colorCode initialization parameter} gets saved in the bundle
     */

    private static final String COLOR_CODE_KEY = "inputCode";
    /**
     * list view that contains information about similar beads
     */
    private ListView mListView;
    /**
     * adapter that is responsible for displaying similar beads in the {@link #mListView list view}.
     */
    private SimilarBeadAdapter mAdapter;


    public SimilarBeadFragment() {
        // Required empty public constructor
    }

    /**
     * instance that performs a bead-related search in database (asynchronously)
     */
    private BeadSearcher mSearcher;

    private SimilarBeadListener mCallback;

    public void setCode(String code) {
        this.colorCode = code;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCallback = (SimilarBeadListener) getActivity();
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        return inflater.inflate(R.layout.fragment_similar_bead, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (savedInstanceState != null) {
            Log.i(Config.TAG, marker + " saved instance is found");
            this.colorCode = savedInstanceState.getString(COLOR_CODE_KEY);
        } else {
            Log.i(Config.TAG, marker + "no saved instance is found");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mListView = (ListView) getView().findViewById(R.id.list_similar);
        this.mAdapter = new SimilarBeadAdapter(getActivity().getApplicationContext(), new ArrayList<String>());
        this.mListView.setAdapter(this.mAdapter);
        if (this.colorCode != null) {
            updateView(this.colorCode);
        }
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.bead_similar_color_code);
                if (tv != null) {
                    mCallback.onSimilarColorCodeClick(tv.getText().toString());
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (this.colorCode != null) {
            Log.i(Config.TAG, marker + " saving code " + this.colorCode);
            outState.putString(COLOR_CODE_KEY, this.colorCode);
        } else {
            Log.i(Config.TAG, marker + " no code to save");
        }
    }


    @Override
    public void onStop() {
        this.mListView.setAdapter(null);
        this.mAdapter = null;
        this.mListView = null;
        super.onStop();
    }


    /**
     * @author veontomo@gmail.com
     */
    public void updateView(String str) {
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        ((TextView) getView().findViewById(R.id.color_code)).setText(str);
        findSimilar(str);

    }

    private void findSimilar(String code) {
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (this.mSearcher == null) {
            Log.i(Config.TAG, "create new mSearcher");
            this.mSearcher = new BeadSearcher(new Storage(getActivity().getApplicationContext()));
        } else {
            Log.i(Config.TAG, "mSearcher already exists");
        }
        this.mSearcher.fillInWithSimilar(code, this.mAdapter);
    }


    /**
     * Interface to be implemented if one wants that the fragment is able to comunicate with
     * its hosting activity.
     */
    public interface SimilarBeadListener {
        void initialize(String str);

        /**
         * action to be executed when a color with given code is clicked
         *
         * @param str color code
         */
        void onSimilarColorCodeClick(String str);
    }


}

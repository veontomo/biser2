package com.veontomo.bead.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.veontomo.bead.Config;
import com.veontomo.bead.Storage;
import com.veontomo.bead.R;
import com.veontomo.bead.api.BeadSearcher;
import com.veontomo.bead.api.SimilarBeadAdapter;

import java.util.ArrayList;


/**
 *
 */
public class SimilarBeadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String marker = "similar: ";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    /**
     * list view that contains information about similar beads
     */
    private ListView mListView;
    /**
     * adapter that is responsible for displaying similar beads in the {@link #mListView list view}.
     */
    private SimilarBeadAdapter mAdapter;
    private SimilarBeadListener mCallback;

    public SimilarBeadFragment() {
        // Required empty public constructor
    }

    /**
     * instance that performs a bead-related search in database (asynchronously)
     */
    private BeadSearcher mSearcher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
//        this.mCallback = (SimilarBeadListener) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString("color");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_similar_bead, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mListView = (ListView) getView().findViewById(R.id.list_similar);
        this.mAdapter = new SimilarBeadAdapter(getActivity().getApplicationContext(), new ArrayList<String>());
        this.mListView.setAdapter(this.mAdapter);
        if (mParam1 != null){
            updateView(mParam1);
        }


    }



    @Override
    public void onStop(){
        this.mListView.setAdapter(null);
        this.mAdapter = null;
        this.mListView = null;
        super.onStop();
    }


    /**
     * @author veontomo@gmail.com
     */
    public void updateView(String str) {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        ((TextView) getView().findViewById(R.id.color_code)).setText(str);
        findSimilar(str);

    }

    private void findSimilar(String code) {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        if (this.mSearcher == null){
            Log.i(Config.TAG, "create new mSearcher");
            this.mSearcher = new BeadSearcher(new Storage(getActivity().getApplicationContext()));
        } else {
            Log.i(Config.TAG, "mSearcher already exists");
        }
        this.mSearcher.fillInWithSimilar(code, this.mAdapter);
    }
//        SimilarBeadFinderTask task = new SimilarBeadFinderTask(this.mAdapter);
//        task.execute(code);



    /**
     * Interface to be implemented if one wants that the fragment is able to comunicate with
     * its hosting activity.
     */
    public interface SimilarBeadListener {
        void sendColor(String str);
    }


}

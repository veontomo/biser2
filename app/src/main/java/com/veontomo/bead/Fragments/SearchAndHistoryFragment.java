package com.veontomo.bead.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.veontomo.bead.Config;
import com.veontomo.bead.R;
import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.BeadFinderTask;
import com.veontomo.bead.Tasks.BeadLoaderTask;
import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.BeadAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBeadSearchListener} interface
 * to handle interaction events.
 */
public class SearchAndHistoryFragment extends Fragment {
    private final String marker = "fragment: ";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * search button
     */
    private ImageButton mButton;

    /**
     * edit text view in which the search term is to be typed
     */
    private EditText mEditText;

    private ListView mListView;

    private OnBeadSearchListener mCallback;


    private BeadAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mCallback = (OnBeadSearchListener) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        BeadLoaderTask loader = new BeadLoaderTask(getActivity().getApplicationContext());
        loader.execute("locations.txt");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        return inflater.inflate(R.layout.fragment_search_and_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mButton = (ImageButton) getView().findViewById(R.id.button);
        this.mEditText = (EditText) getView().findViewById(R.id.editText);
        this.mListView = (ListView) getView().findViewById(R.id.searchHistory);
        this.mAdapter = new BeadAdapter(getActivity().getApplicationContext(), new ArrayList<Bead>());
        this.mListView.setAdapter(this.mAdapter);
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = mEditText.getEditableText().toString();
                searchTerm = searchTerm.replaceAll("[^0-9.]", "");
                if (mCallback != null) {
                    mCallback.onColorCodeReceived(searchTerm);
                } else {
                    Log.i(Config.TAG, "Activity that hosts the fragment does not implement interface SearchAndHistoryFragment.OnBeadSearchListener. Therefore, no data exchange is possible.");
                }
                BeadFinderTask worker = new BeadFinderTask(new Storage(getActivity().getApplicationContext()), mAdapter, mCallback);
                worker.execute(searchTerm);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.mButton.setOnClickListener(null);
        this.mListView.setAdapter(null);
        this.mAdapter = null;
        this.mListView = null;
        this.mEditText = null;
        this.mButton = null;
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDetach();
//        mCallback = null;
    }


    public SearchAndHistoryFragment() {
        // Required empty public constructor
    }


    /**
     * Interface to be implemented if one wants that the fragment is able to comunicate with
     * its hosting activity.
     */
    public interface OnBeadSearchListener {
        /**
         * action to be executed when the search term is received
         * @param str search term
         */
        void onColorCodeReceived(String str);
        /**
         * action to be executed when given color code is not found
         * @param str color code
         */
        void onColorCodeAbsent(String str);
    }

}
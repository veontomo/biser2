package com.veontomo.biser2.Fragments;

import android.app.Activity;
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

import com.veontomo.biser2.Config;
import com.veontomo.biser2.R;
import com.veontomo.biser2.Storage;
import com.veontomo.biser2.Tasks.BeadFinderTask;
import com.veontomo.biser2.api.Bead;
import com.veontomo.biser2.api.BeadAdapter;
import com.veontomo.biser2.api.Location;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchAndHistoryFragment.OnFragmentInteractionListener} interface
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

    private OnFragmentInteractionListener mCallback;


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
        this.mCallback = (OnFragmentInteractionListener) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


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
                if (mCallback != null) {
                    mCallback.acceptSearchTerm(searchTerm);
                } else {
                    Log.i(Config.TAG, "Activity that hosts the fragment does not implement interface SearchAndHistoryFragment.OnFragmentInteractionListener. Therefore, no data exchange is possible.");
                }
                BeadFinderTask worker = new BeadFinderTask(new Storage(getActivity().getApplicationContext()), mAdapter);
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void acceptSearchTerm(String str);
    }

}

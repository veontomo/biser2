package com.veontomo.bead.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
    /**
     * name of the key under which search terms are saved in the bundle
     */
    private static final String SEARCH_TERMS_KEY = "searchTerms";
    private final String marker = "search & history fragment: ";
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
    /**
     * String version of all search terms along with corresponding search results.
     */
    private String searchTerms;

    public SearchAndHistoryFragment() {
        // Required empty public constructor
    }

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
        if (savedInstanceState != null) {
            searchTerms = savedInstanceState.getString(SEARCH_TERMS_KEY);
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
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCallback == null) {
                    return;
                }
                TextView tv = (TextView) view.findViewById(R.id.bead_present_color_code);
                if (tv == null) {
                    tv = (TextView) view.findViewById(R.id.bead_absent_color_code);
                }
                if (tv != null) {
                    mCallback.onColorCodeClick(tv.getText().toString());
                }
            }
        });
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = mEditText.getEditableText().toString();
                searchTerm = searchTerm.replaceAll("[^0-9.]", "");
                mEditText.setText("");
                if (mCallback != null) {
                    mCallback.onColorCodeReceived(searchTerm);
                }
                if (!searchTerm.isEmpty()) {
                    updateSearchResult(searchTerm);
                }
            }
        });

        if (this.searchTerms != null) {
            Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName() + " INFLATING");
            mAdapter.inflate(searchTerms);
            mAdapter.notifyDataSetChanged();
        } else {
            Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName() + " nothing to INFLATE");
        }
    }

    private void updateSearchResult(String code) {
        int index = mAdapter.getPositionByColorCode(code);
        if (index != -1) {
            Bead bead = mAdapter.removeItem(index);
            mAdapter.prependItem(bead);
            mAdapter.notifyDataSetChanged();
        } else {
            BeadFinderTask worker = new BeadFinderTask(new Storage(getActivity().getApplicationContext()), mAdapter, mCallback);
            worker.execute(code);
        }

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.searchTerms = mAdapter.stringify();
        Log.i(Config.TAG, marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        Log.i(Config.TAG, marker + " saving " + this.searchTerms);
        outState.putString(SEARCH_TERMS_KEY, this.searchTerms);
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

    /**
     * Performs search of given color code
     * @param str color code to search
     */
    public void insert(String str) {
        updateSearchResult(str);
    }


    /**
     * Interface to be implemented if one wants that the fragment is able to communicate with
     * its hosting activity.
     */
    public interface OnBeadSearchListener {
        /**
         * action to be executed when the search term is received
         *
         * @param str search term
         */
        void onColorCodeReceived(String str);

        /**
         * action to be executed when given color code is not found
         *
         * @param str color code
         */
        void onColorCodeAbsent(String str);

        /**
         * action to be executed when a color with given code is clicked
         *
         * @param str color code
         */
        void onColorCodeClick(String str);
    }

}

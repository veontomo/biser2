package com.veontomo.bead.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.veontomo.bead.Storage;
import com.veontomo.bead.Tasks.SimilarBeadFinderTask;
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

    /**
     * instance that performs a bead-related search in database (asynchronously)
     */
    private BeadSearcher mSearcher;


    public SimilarBeadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
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
    public void onStart(){
        super.onStart();
        this.mListView = (ListView) getView().findViewById(R.id.list_similar);
        this.mAdapter = new SimilarBeadAdapter(getActivity().getApplicationContext(), new ArrayList<String>());
        this.mListView.setAdapter(this.mAdapter);
    }

    @Override
    public void onStop(){
        this.mListView.setAdapter(null);
        this.mAdapter = null;
        this.mListView = null;
        super.onStop();
    }


    /**
     *
     * @author veontomo@gmail.com
     */
    public void updateView(String str) {
        ((TextView) getView().findViewById(R.id.color_code)).setText(str);
        findSimilar(str);

    }

    private void findSimilar(String code) {
        if (this.mSearcher == null){
            this.mSearcher = new BeadSearcher(new Storage(getActivity().getApplicationContext()));
        }
        this.mSearcher.fillInWithSimilar(code, this.mAdapter);
//        SimilarBeadFinderTask task = new SimilarBeadFinderTask(this.mAdapter);
//        task.execute(code);

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}

package com.veontomo.biser2.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.veontomo.biser2.Config;
import com.veontomo.biser2.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {
 *
 * @link SearchAndHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchAndHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
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

//    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
//            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Config.TAG, this.marker + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Config.TAG, "click");
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
//        mListener = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchAndHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchAndHistoryFragment newInstance(String param1, String param2) {
        SearchAndHistoryFragment fragment = new SearchAndHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchAndHistoryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
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

package com.maxapp.dansu.astraplayer.folder_browser_activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.FolderBrowser;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SDfoldersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SDfoldersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SDfoldersFragment extends Fragment implements DirectoryBrowserAdapter.ListAdapterListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView folderRecyclerView;
    public List<MyDirectory> sdDirectories;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SDfoldersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SDfoldersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SDfoldersFragment newInstance(String param1, String param2) {
        SDfoldersFragment fragment = new SDfoldersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View fragmentView = inflater.inflate(R.layout.fragment_sdfolders, container, false);

        SharedPreferencesEditor sp = new SharedPreferencesEditor(getActivity());
        sdDirectories = sp.ReadDirectories("sdDirectories");

        if(sdDirectories == null)
            sdDirectories = new ArrayList<MyDirectory>();

        folderRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.folderRV);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getContext());
        folderRecyclerView.setLayoutManager(recyclerLayoutManager);


        FolderBrowser folderBrowser = new FolderBrowser(getActivity());
        List<MyDirectory> list = folderBrowser.getSdContent();



        //RecyclerView adapater
        DirectoryBrowserAdapter recyclerViewAdapter = new
                DirectoryBrowserAdapter(list,sdDirectories, getActivity(), this);
        folderRecyclerView.setAdapter(recyclerViewAdapter);

        return fragmentView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Add(MyDirectory dir) {
        mListener.Add(dir, true);
    }

    @Override
    public void Del(MyDirectory dir) {
        mListener.Del(dir, true);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void Add(MyDirectory dir, boolean isSd);
        void Del(MyDirectory dir, boolean isSd);
    }
}

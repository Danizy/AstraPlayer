package com.maxapp.dansu.astraplayer.folder_browser_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


public class LocalFoldersFragment extends Fragment implements DirectoryBrowserAdapter.ListAdapterListener {

    public List<MyDirectory> localDirectories;

    RecyclerView folderRecyclerView;


    private OnFragmentInteractionListener mListener;

    public LocalFoldersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_local_folders, container, false);

        SharedPreferencesEditor sp = new SharedPreferencesEditor(getActivity());
        localDirectories = sp.ReadDirectories("localDirectories");

        if(localDirectories == null)
            localDirectories = new ArrayList<>();

        folderRecyclerView = fragmentView.findViewById(R.id.folderRV);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getContext());
        folderRecyclerView.setLayoutManager(recyclerLayoutManager);


        FolderBrowser folderBrowser = new FolderBrowser(getContext());
        List<MyDirectory> list = folderBrowser.getLocalContent();




        DirectoryBrowserAdapter recyclerViewAdapter = new
                DirectoryBrowserAdapter(list,localDirectories, getActivity(), this);
        folderRecyclerView.setAdapter(recyclerViewAdapter);

        return fragmentView;

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
        mListener.Add(dir, false);
    }

    @Override
    public void Del(MyDirectory dir) {
        mListener.Del(dir, false);
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

        void Add(MyDirectory dir, boolean isSd);
        void Del(MyDirectory dir, boolean isSd);
    }
}

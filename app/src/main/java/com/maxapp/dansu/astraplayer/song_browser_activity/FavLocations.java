package com.maxapp.dansu.astraplayer.song_browser_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maxapp.dansu.astraplayer.MainActivity;
import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.FolderBrowser;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import com.maxapp.dansu.astraplayer.folder_browser_activity.DirectoryBrowserAdapter;
import com.maxapp.dansu.astraplayer.folder_browser_activity.FolderBrowserActivity;

import java.util.List;

public class FavLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_locations);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.favRecycler);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(recyclerLayoutManager);

        SharedPreferencesEditor sp = new SharedPreferencesEditor(this);

        List<MyDirectory> directories = sp.ReadDirectories("localDirectories");
        directories.addAll(sp.ReadDirectories("sdDirectories"));




        FavLocationAdapter adapter = new FavLocationAdapter(directories, this);
        recycler.setAdapter(adapter);
    }

    public void browse(View v){
        Intent intent = new Intent(this,
                FolderBrowserActivity.class);
        startActivity(intent);
    }


}

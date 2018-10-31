package com.maxapp.dansu.astraplayer.song_browser_activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maxapp.dansu.astraplayer.MainActivity;
import com.maxapp.dansu.astraplayer.MusicService.MusicService;
import com.maxapp.dansu.astraplayer.R;
import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.FolderBrowser;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import com.maxapp.dansu.astraplayer.folder_browser.MyFile;

import java.util.List;

public class FavSong extends AppCompatActivity implements FavSongAdapter.FavSongListener {

    MusicService mService;
    RecyclerView recycler;
    List<MyFile> files;
    int folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_song);

        recycler = (RecyclerView) findViewById(R.id.favRecycler);

        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(recyclerLayoutManager);

        SharedPreferencesEditor sp = new SharedPreferencesEditor(this);

        List<MyDirectory> directories = sp.ReadDirectories("localDirectories");
        directories.addAll(sp.ReadDirectories("sdDirectories"));

        FolderBrowser fb = new FolderBrowser(this);
        folder = getIntent().getIntExtra("folder", 0);

        files = fb.getFiles(directories.get(folder).directory);

        Intent intent = new Intent(this, MusicService.class);
        FavSongAdapter adapter = new FavSongAdapter(files, getBaseContext(), this);
        recycler.setAdapter(adapter);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();

            if(mService.isMediaPlayerReady()){

            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    };

    @Override
    public void setSong(MyFile song) {
        mService.SetSong(song);
        Intent intent = new Intent(this,
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("folder", folder);
        intent.putExtra("song", files.indexOf(song));
        startActivity(intent);
    }
}

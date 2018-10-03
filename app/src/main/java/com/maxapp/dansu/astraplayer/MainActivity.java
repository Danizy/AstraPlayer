package com.maxapp.dansu.astraplayer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.maxapp.dansu.astraplayer.MusicService.DataHolder;
import com.maxapp.dansu.astraplayer.MusicService.MusicService;
import com.maxapp.dansu.astraplayer.folder_browser_activity.FolderBrowserActivity;

public class MainActivity extends AppCompatActivity {

    MusicService mService; //holder of music player
    boolean mBound = false;
    DataHolder Dh; //music, folders, current song/folder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dh = new DataHolder(this);

        showPhoneStatePermission(); //Permission check
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBound){
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mBound = false;
    }


    ////////////////////////////////////////////////////////////////////// PERMISSION REQUEST
    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Permission Needed", "This app needs permission to read data on your phone", Manifest.permission.READ_EXTERNAL_STORAGE, 1);
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
            }
        } else {
            checkFirstLaunch();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkFirstLaunch();
                } else {
                    finish();
                    System.exit(0);
                }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }


    ///////////////////////////////////////////////////////////////// CHECK FIRST LAUNCH
    private void checkFirstLaunch(){


        SharedPreferencesEditor editor = new SharedPreferencesEditor(this);
        boolean test = editor.GetBoolean("firstLaunch");
        if(test){
            loadData();
            return;
        }
        startActivity(new Intent(MainActivity.this, FirstLaunch.class));
        editor.WriteBoolean("firstLaunch", true);
    }


    ///////////////////////////////////////////////////////////////// MUSIC SERVICE CONNECTION
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            if(!mService.isPlaying())
                mService.SetSong(Dh.getCurrentSong());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };


    ///////////////////////////////////////////////////////////////// LOAD SAVED DATA

    private void loadData(){

        Dh.refresh();

    }

    public void OnBtnClick(View v){
        if(mBound){
            mService.Play();
        }

    }

    public void NextSong(View v){
        mService.SetSong(Dh.getNextSong());
    }

    public void PreviousSong(View v){
        mService.SetSong(Dh.getPreviousSong());
    }

    public void PreviousFolder(View v){
        mService.SetSong(Dh.getPreviousFolderSong());
    }

    public void NextFolder(View v){
        mService.SetSong(Dh.getNextFolderSong());
    }

    public void Browse(View v){
        startActivityForResult(new Intent(MainActivity.this, FolderBrowserActivity.class), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Dh.refresh();
                mService.SetSong(Dh.getCurrentSong());
            }
        }
    }
}

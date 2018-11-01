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
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maxapp.dansu.astraplayer.MusicService.DataHolder;
import com.maxapp.dansu.astraplayer.MusicService.MusicService;
import com.maxapp.dansu.astraplayer.folder_browser_activity.FolderBrowserActivity;
import com.maxapp.dansu.astraplayer.song_browser_activity.FavLocations;

public class MainActivity extends Activity {

    MusicService mService; //holder of music player
    boolean mBound = false;
    DataHolder Dh; //music, folders, current song/folder
    ConstraintLayout layout;
    MotionDetector motion;
    SeekBar mSeekBar;
    TextView TitleTextVIew;
    TextView FolderTextView;
    ImageView imgView;
    ImageAnimator imageAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MusicService.class));
        Dh = new DataHolder(this);
        motion = new MotionDetector();
        showPhoneStatePermission(); //Permission check
        mSeekBar = findViewById(R.id.seekBar);
        TitleTextVIew = findViewById(R.id.TitleTextView);
        FolderTextView = findViewById(R.id.FolderTextView);
        imgView = findViewById(R.id.imageView2);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        imageAnimator = new ImageAnimator(lp.leftMargin, lp.topMargin, Dh);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBound){
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
        else{


            //if(getIntent().getIntExtra("song", 9999) != 9999){
            //    Dh.setFolderAndSong(getIntent().getIntExtra("folder", 0), getIntent().getIntExtra("song", 0));
            //}

            TitleTextVIew.setText(Dh.getSongName());
            FolderTextView.setText(Dh.getFolderName());
        }

        final Handler mHandler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mBound){
                    int mCurrentPosition = mService.getCurrentPosition();
                    mSeekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });



        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(fromUser)
                    mService.setPosition(progress);

            }
        }); //Seekbar control
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("currentFolder", Dh.getFolderId());
        savedInstanceState.putInt("currentSong", Dh.getSongId());

        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        Dh.setFolderAndSong(savedInstanceState.getInt("currentFolder"), savedInstanceState.getInt("currentSong"));
        imgView.setImageBitmap(Dh.getSongImage());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();
        int a = extras.getInt("song", 9999);
        int b = extras.getInt("folder", 9999);
        Dh.setFolderAndSong(b, a);
        TitleTextVIew.setText(Dh.getSongName());
        FolderTextView.setText(Dh.getFolderName());
        imgView.setImageBitmap(Dh.getSongImage());
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
            if(!mService.isPlaying()){
                mService.SetSong(Dh.getCurrentSong());
                mService.Pause();
            }
            if(mService.isMediaPlayerReady()){
                mSeekBar.setMax(mService.getDuration());
                TitleTextVIew.setText(Dh.getSongName());
                FolderTextView.setText(Dh.getFolderName());
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
            mService = null;
        }
    };


    ///////////////////////////////////////////////////////////////// LOAD SAVED DATA

    private void loadData(){

        Dh.refresh();

    }

    ///////////////////////////////////////////////////////////////// TOUCH EVENTS
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String action = motion.move(event);
        if(action == "left"){
            NextSong(this.findViewById(R.id.main_content));
            imageAnimator.animateLeft(this, imgView);
        }
        else if(action == "right"){
            PreviousSong(this.findViewById(R.id.main_content));
            imageAnimator.animateRight(this, imgView);
        }
        else if(action == "down"){
            PreviousFolder(this.findViewById(R.id.main_content));
            imageAnimator.animateDown(this, imgView);
        }
        else if(action == "up"){
            NextFolder(this.findViewById(R.id.main_content));
            imageAnimator.animateUp(this, imgView);

        }
        else if(action == "doubleTap"){
            PlayPause(this.findViewById(R.id.main_content));
        }

        imageAnimator.animate(event, imgView);

        return super.onTouchEvent(event);
    }

    public void PlayPause(View v){
        if(mBound){
            if(mService.isPlaying())
                mService.Pause();
            else
                mService.Play();
        }

    }

    public void NextSong(View v){
        mService.SetSong(Dh.getNextSong());
        mSeekBar.setMax(mService.getDuration());
        TitleTextVIew.setText(Dh.getSongName());
    }

    public void PreviousSong(View v){
        mService.SetSong(Dh.getPreviousSong());
        mSeekBar.setMax(mService.getDuration());
        TitleTextVIew.setText(Dh.getSongName());
    }

    public void PreviousFolder(View v){
        mService.SetSong(Dh.getPreviousFolderSong());
        mSeekBar.setMax(mService.getDuration());
        TitleTextVIew.setText(Dh.getSongName());
        FolderTextView.setText(Dh.getFolderName());
    }

    public void NextFolder(View v){
        mService.SetSong(Dh.getNextFolderSong());
        mSeekBar.setMax(mService.getDuration());
        TitleTextVIew.setText(Dh.getSongName());
        FolderTextView.setText(Dh.getFolderName());
    }

    public void Browse(View v){
        //startActivityForResult(new Intent(MainActivity.this, FolderBrowserActivity.class), 1);
        startActivityForResult(new Intent(MainActivity.this, FavLocations.class), 1);
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

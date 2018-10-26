package com.maxapp.dansu.astraplayer.MusicService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.maxapp.dansu.astraplayer.folder_browser.MyFile;

import java.io.IOException;

public class MusicService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private Boolean Playing = false;
    private Boolean mediaPlayerReady = false;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public class LocalBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }



    public void SetSong(MyFile song){
        if(song == null)
            return;
        try {
            if(Playing)
                mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(song.location));
            mediaPlayer.prepare();
            mediaPlayerReady = true;
            mediaPlayer.start();
            Playing = true;
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayerReady = false;
        }
    }

    public void Play(){
        if(mediaPlayerReady){
            mediaPlayer.start();
            Playing = true;
        }

    }

    public void Pause(){
        mediaPlayer.pause();
        Playing = false;
    }

    public Boolean isPlaying(){
        return Playing;
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public void setPosition(int position){
        mediaPlayer.seekTo(position);
    }

    public Boolean isMediaPlayerReady(){
        return mediaPlayerReady;
    }


}

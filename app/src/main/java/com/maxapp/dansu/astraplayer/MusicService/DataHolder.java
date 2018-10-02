package com.maxapp.dansu.astraplayer.MusicService;

import android.app.Activity;

import com.maxapp.dansu.astraplayer.SharedPreferencesEditor;
import com.maxapp.dansu.astraplayer.folder_browser.FolderBrowser;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import com.maxapp.dansu.astraplayer.folder_browser.MyFile;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {
    private Activity act;
    private List<MyDirectory> directories;
    private List<MyFile> files;
    private SharedPreferencesEditor SPEditor;
    private FolderBrowser Fb;
    private int currentSong = 0;
    private int currentFolder = 0;


    public DataHolder(Activity act){
        this.act = act;
        directories = new ArrayList<MyDirectory>();
        files = new ArrayList<MyFile>();
        SPEditor = new SharedPreferencesEditor(act);
        Fb = new FolderBrowser(act);
    }

    public void refresh(){
        directories = SPEditor.ReadDirectories("localDirectories");
        directories.addAll(SPEditor.ReadDirectories("sdDirectories"));
        currentSong = 0;
        currentFolder = 0;
        files = Fb.getFiles(directories.get(0).directory);
    }

    public MyFile getCurrentSong(){return files.get(currentSong);}

    public MyFile getSong(int id){
        if(id < 0 || id > files.size() - 1)
            return files.get(0);
        return files.get(id);
    }

    public MyFile getNextSong(){
        currentSong++;
        if(currentSong > files.size() - 1)
            currentSong = 0;

        return files.get(currentSong);
    }

    public MyFile getPreviousSong(){
        currentSong--;
        if(currentSong < 0)
            currentSong = files.size() - 1;
        return files.get(currentSong);
    }

    public MyFile getNextFolderSong(){
        currentFolder++;
        currentSong = 0;
        if(currentFolder > directories.size() - 1)
            currentFolder = 0;
        files = Fb.getFiles(directories.get(currentFolder).directory);
        if(files.size() == 0)
            return null;
        return files.get(currentSong);
    }

    public MyFile getPreviousFolderSong(){
        currentFolder--;
        currentSong = 0;
        if(currentFolder < 0)
            currentFolder = directories.size() - 1;
        files = Fb.getFiles(directories.get(currentFolder).directory);
        if(files.size() == 0)
            return null;
        return files.get(currentSong);
    }
}

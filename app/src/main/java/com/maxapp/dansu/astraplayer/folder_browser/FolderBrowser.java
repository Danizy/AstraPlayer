package com.maxapp.dansu.astraplayer.folder_browser;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderBrowser {
    Context context;

    public FolderBrowser(Context ctx){
        this.context = ctx;
    }


    public List<MyDirectory> getLocalContent(){
        File directory = new File(Environment.getExternalStorageDirectory().toString());
        File[] files = directory.listFiles();
        List<MyDirectory> localFolders = new ArrayList<MyDirectory>();
        if(files == null || files.length == 0)
            return null;
        for(File infile : files){
            if(!infile.isDirectory())
                continue;
            if(infile.getName().charAt(0) == '.')
                continue;
            localFolders.add(new MyDirectory(infile.getName(), infile.getAbsolutePath()));

        }
        return localFolders;
    }

    public List<MyDirectory> getSdContent(){

        try {

            if(StorageOptions.getExternalStorageDirectories(context)[0].equals(Environment.getExternalStorageDirectory().toString()))

                return null;
            File directory = new File(StorageOptions.getExternalStorageDirectories(context)[0]);
            File[] files = directory.listFiles();
            List<MyDirectory> sdFolders = new ArrayList<MyDirectory>();
            if(files == null || files.length == 0)
                return null;
            for(File infile : files){
                if(infile.isDirectory()) {
                    if(infile.getName().charAt(0) == '.')
                        continue;
                    sdFolders.add(new MyDirectory(infile.getName(), infile.getAbsolutePath()));

                }
            }
            return sdFolders;
        } catch (Exception e){
            return null;
        }
    }

}

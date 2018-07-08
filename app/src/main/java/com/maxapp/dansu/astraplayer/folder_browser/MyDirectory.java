package com.maxapp.dansu.astraplayer.folder_browser;

import java.util.Objects;

public class MyDirectory {
    public String name;
    public String directory;

    public MyDirectory(String name, String directory){
        this.name = name;
        this.directory = directory;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        MyDirectory myDirectory = (MyDirectory) o;
        boolean a = name.equals(myDirectory.name);
        boolean b = directory.equals(myDirectory.directory);
        return name.equals(myDirectory.name) &&
                directory.equals(myDirectory.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, directory);
    }
}

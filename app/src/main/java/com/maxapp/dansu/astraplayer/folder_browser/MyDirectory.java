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
        if (o == null || getClass() != o.getClass()) return false;
        MyDirectory myDirectory = (MyDirectory) o;
        return name.equals(myDirectory.name) &&
                directory.equals(myDirectory.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, directory);
    }
}

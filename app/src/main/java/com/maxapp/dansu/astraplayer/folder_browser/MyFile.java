package com.maxapp.dansu.astraplayer.folder_browser;

import java.util.Objects;

public class MyFile {
    public String name;
    public String location;

    MyFile(String name, String location){
        this.name = name;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyFile myFile = (MyFile) o;
        return Objects.equals(name, myFile.name) &&
                Objects.equals(location, myFile.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, location);
    }
}

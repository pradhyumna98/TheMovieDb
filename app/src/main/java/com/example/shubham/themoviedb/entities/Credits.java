package com.example.shubham.themoviedb.entities;

import java.util.List;

/**
 * Created by shubham on 7/26/2018.
 */

public class Credits {
    long id;
    List<People> cast;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<People> getCast() {
        return cast;
    }

    public void setCast(List<People> cast) {
        this.cast = cast;
    }
}

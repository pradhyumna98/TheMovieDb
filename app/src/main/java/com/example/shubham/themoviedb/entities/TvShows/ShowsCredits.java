package com.example.shubham.themoviedb.entities.TvShows;

import com.example.shubham.themoviedb.entities.Movies.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 7/30/2018.
 */

public class ShowsCredits {
    ArrayList<Shows> cast;

    public ArrayList<Shows> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Shows> cast) {
        this.cast = cast;
    }
}

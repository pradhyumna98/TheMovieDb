package com.example.shubham.themoviedb.entities.Movies;

import com.example.shubham.themoviedb.entities.TvShows.Shows;

import java.util.ArrayList;

/**
 * Created by shubham on 7/30/2018.
 */

public class MovieCredits {
    public ArrayList<Movie> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Movie> cast) {
        this.cast = cast;
    }

    ArrayList<Movie> cast;

}

package com.example.shubham.themoviedb.Adapter;

import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.TvShows.Shows;

/**
 * Created by shubham on 7/27/2018.
 */

public interface SearchRowListener {
   public void onMovieItemClicked(int position, Movie movie);
    public void onShowItemClicked(int position, Shows show);
    public void onPeopleItemClicked(int position, People people);
}

package com.example.shubham.themoviedb.entities.Movies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.shubham.themoviedb.entities.Movies.Movie;

/**
 * Created by shubham on 7/29/2018.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Movie.class,parentColumns = "id",childColumns = "movieId")})
public class FavouriteMovies {
    @PrimaryKey(autoGenerate = true)
    int id;
    long movieId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
}

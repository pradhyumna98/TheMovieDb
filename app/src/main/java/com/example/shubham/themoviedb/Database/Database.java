package com.example.shubham.themoviedb.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shubham.themoviedb.entities.Movies.FavouriteMovies;
import com.example.shubham.themoviedb.entities.Movies.Movie;

import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.FavouriteShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;

import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

/**
 * Created by shubham on 7/17/2018.
 */
@android.arch.persistence.room.Database(entities = {Movie.class, FavouriteShows.class,FavouriteMovies.class,UpcomingMovie.class,NowShowingMovie.class,TopRatedMovie.class, PopularMovie.class, Shows.class, TopRatedShows.class, PopularShows.class, AirTodayShows.class, OnAirShows.class},version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase{
public abstract MovieDAO getMovieDAO();
public abstract ShowDAO getShowDAO();
private static Database database;
public static Database getInstance(Context context)
{
    if(database==null)
    {
        database= Room.databaseBuilder(context.getApplicationContext(),Database.class,"movie_db").allowMainThreadQueries().build();
    }
    return database;
}
}

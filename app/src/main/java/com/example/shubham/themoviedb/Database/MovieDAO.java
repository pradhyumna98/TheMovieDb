package com.example.shubham.themoviedb.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;

import java.util.List;

/**
 * Created by shubham on 7/17/2018.
 */
@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUpcomingMovies(List<UpcomingMovie> upcomingMovies);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNowShowingMovies(List<NowShowingMovie> nowShowingMovies);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopRatedMovies(List<TopRatedMovie> topRatedMovies);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPopularMovies(List<PopularMovie> popularMovies);
    @Query("Select * from Movie where id in (:ids) order by popularity desc")
    List<Movie> getNowUpMovies(int[] ids);
    @Query("Select * from Movie where id in (:ids) order by voteAverage desc")
    List<Movie> getTopPopMovies(int[] ids);
    @Query("Select movieId from NowShowingMovie")
    int[] getNowShowing();
    @Query("Select movieId from UpcomingMovie")
    int[] getUpcomingMovie();
    @Query("Select movieId from TopRatedMovie")
    int[] getTopRatedMovie();
    @Query("Select movieId from PopularMovie")
    int[] getPopularMovie();

}

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
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

import java.util.List;

/**
 * Created by shubham on 7/21/2018.
 */
@Dao
public interface ShowDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShows(List<Shows> movies);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOnAirShows(List<OnAirShows> onAirShows);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAirTodayShows(List<AirTodayShows> airTodayShows);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopRatedShows(List<TopRatedShows> topRatedShows);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPopularShows(List<PopularShows> popularShows);
    @Query("Select * from Shows where id in (:ids) order by popularity desc")
    List<Shows> getAirShows(int[] ids);
    @Query("Select * from Shows where id in (:ids) order by voteAverage desc")
    List<Shows> getTopPopShows(int[] ids);
    @Query("Select showId from OnAirShows")
    int[] getOnAirShows();
    @Query("Select showId from AirTodayShows")
    int[] getAirTodayShows();
    @Query("Select showId from TopRatedShows")
    int[] getTopRatedShows();
    @Query("Select showId from PopularShows")
    int[] getPopularShows();
}

package com.example.shubham.themoviedb.Networking;

import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shubham on 7/15/2018.
 */

public interface MovieDBService {
    @GET("movie/upcoming")
    Call<UpcomingMovie> getUpComingMovies(@Query("api_key") String key);
    @GET("movie/now_playing")
    Call<NowShowingMovie> getNowShowingMovies(@Query("api_key") String key);
    @GET("movie/top_rated")
    Call<TopRatedMovie> getTopRatedMovies(@Query("api_key") String key);
    @GET("movie/popular")
    Call<PopularMovie> getPopularMovies(@Query("api_key") String key);
    @GET("tv/on_the_air")
    Call<OnAirShows> getOnAirShows(@Query("api_key") String key);
    @GET("tv/airing_today")
    Call<AirTodayShows> getAirTodayShows(@Query("api_key") String key);
    @GET("tv/top_rated")
    Call<TopRatedShows> getTopRatedShows(@Query("api_key") String key);
    @GET("tv/popular")
    Call<PopularShows> getPopularShows(@Query("api_key") String key);
}

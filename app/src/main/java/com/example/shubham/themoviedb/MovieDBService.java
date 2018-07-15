package com.example.shubham.themoviedb;

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
}

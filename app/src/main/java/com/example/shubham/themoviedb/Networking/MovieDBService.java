package com.example.shubham.themoviedb.Networking;

import com.example.shubham.themoviedb.entities.Credits;
import com.example.shubham.themoviedb.entities.Movies.Movie;

import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.SimilarMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;

import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.TvShows.SimilarShow;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shubham on 7/15/2018.
 */

public interface MovieDBService {
    @GET("movie/upcoming")
    Call<UpcomingMovie> getUpComingMovies(@Query("api_key") String key,@Query("page") int page);
    @GET("movie/now_playing")
    Call<NowShowingMovie> getNowShowingMovies(@Query("api_key") String key,@Query("page") int page);
    @GET("movie/top_rated")
    Call<TopRatedMovie> getTopRatedMovies(@Query("api_key") String key,@Query("page") int page);
    @GET("movie/popular")
    Call<PopularMovie> getPopularMovies(@Query("api_key") String key,@Query("page") int page);
    @GET("movie/{movie_id}/similar")
    Call<SimilarMovie> getSimilarMovies(@Path("movie_id") long id,@Query("api_key") String key,@Query("page") int page);
    @GET("tv/on_the_air")
    Call<OnAirShows> getOnAirShows(@Query("api_key") String key,@Query("page") int page);
    @GET("tv/airing_today")
    Call<AirTodayShows> getAirTodayShows(@Query("api_key") String key,@Query("page") int page);
    @GET("tv/top_rated")
    Call<TopRatedShows> getTopRatedShows(@Query("api_key") String key,@Query("page") int page);
    @GET("tv/popular")
    Call<PopularShows> getPopularShows(@Query("api_key") String key,@Query("page") int page);
    @GET("tv/{tv_id}/similar")
    Call<SimilarShow> getSimilarShows(@Path("tv_id") long id, @Query("api_key") String key, @Query("page") int page);
    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") long id,@Query("api_key") String key);
    @GET("tv/{tv_id}")
    Call<Shows> getShowDetails(@Path("tv_id") long id,@Query("api_key") String key);
    @GET("movie/{movie_id}/credits")
    Call<Credits> getMovieCreditDetails(@Path("movie_id") long id, @Query("api_key") String key);
    @GET("tv/{tv_id}/credits")
    Call<Credits> getShowCreditDetails(@Path("tv_id") long id, @Query("api_key") String key);
    @GET("search/multi")
    Call<ResponseBody> getSearchResult(@Query("api_key") String key,@Query("query") String search,@Query("page") int page);
}

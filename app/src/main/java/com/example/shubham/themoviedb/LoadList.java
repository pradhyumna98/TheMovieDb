package com.example.shubham.themoviedb;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shubham.themoviedb.Adapter.RowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Adapter.ShowsTvAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.Networking.ApiClient;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shubham on 7/23/2018.
 */

public class LoadList {
    String type;

    MovieDAO movieDAO;
    ShowDAO showDAO;
    List<Movie> movies=new ArrayList<>();
    List<Shows> shows=new ArrayList<>();
    Database database;
    ListLoadListener listener;
    public LoadList(Bundle bundle,Context context,ListLoadListener listener)
    {
        type=bundle.getString(Constants.TYPE);
        database= Database.getInstance(context);
        this.listener=listener;
    }
    public List<Movie> getMovies(int Page,long id)
    {
        movieDAO=database.getMovieDAO();
        movies=new ArrayList<>();
        if(type.equals(Constants.NOW_SHOWING_MOVIES))
        {
            int[] nowShowingIds;
            nowShowingIds=movieDAO.getNowShowing();
            movies.addAll(movieDAO.getNowUpMovies(nowShowingIds));
            Call<NowShowingMovie> callNowShowing= ApiClient.getMovieDBService().getNowShowingMovies(Constants.API_KEY,Page);
            callNowShowing.enqueue(new Callback<NowShowingMovie>() {
                @Override
                public void onResponse(Call<NowShowingMovie> call, Response<NowShowingMovie> response) {
                    if(response.body()!=null)
                    {
                        NowShowingMovie nowShowingResponse;
                        List<NowShowingMovie> nowShowings=new ArrayList<>();
                        nowShowingResponse=response.body();
                        movieDAO.insertMovies(nowShowingResponse.getResults());
                        for(int i=0;i<nowShowingResponse.getResults().size();i++)
                        {
                            NowShowingMovie nowShowing=new NowShowingMovie();
                            nowShowing.setMovieId(nowShowingResponse.getResults().get(i).getId());
                            nowShowings.add(nowShowing);
                        }
                        movieDAO.insertNowShowingMovies(nowShowings);
                        movies.clear();
                        movies.addAll(nowShowingResponse.getResults());
                        listener.onMoviesListLoaded(movies);
                    }
                }

                @Override
                public void onFailure(Call<NowShowingMovie> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.POPULAR_MOVIES))
        {
            int[] popularIds;
            popularIds=movieDAO.getPopularMovie();
            movies.addAll(movieDAO.getTopPopMovies(popularIds));
            Call<PopularMovie> callPopular= ApiClient.getMovieDBService().getPopularMovies(Constants.API_KEY,Page);
            callPopular.enqueue(new Callback<PopularMovie>() {
                @Override
                public void onResponse(Call<PopularMovie> call, Response<PopularMovie> response) {
                 if(response.body()!=null)
                 {
                     PopularMovie popularResponse;
                     List<PopularMovie>populars=new ArrayList<>();
                     popularResponse=response.body();
                     movieDAO.insertMovies(popularResponse.getResults());
                     for(int i=0;i<popularResponse.getResults().size();i++)
                     {
                         PopularMovie popular=new PopularMovie();
                         popular.setMovieId(popularResponse.getResults().get(i).getId());
                         populars.add(popular);
                     }
                     movieDAO.insertPopularMovies(populars);
                     movies.clear();
                     movies.addAll(popularResponse.getResults());
                     listener.onMoviesListLoaded(movies);
                 }

                }

                @Override
                public void onFailure(Call<PopularMovie> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.TOP_RATED_MOVIES))
        {
            int[] topRatedIds;
            topRatedIds=movieDAO.getTopRatedMovie();
            movies.addAll(movieDAO.getTopPopMovies(topRatedIds));
            Call<TopRatedMovie> callTopRated= ApiClient.getMovieDBService().getTopRatedMovies(Constants.API_KEY,Page);
            callTopRated.enqueue(new Callback<TopRatedMovie>() {
                @Override
                public void onResponse(Call<TopRatedMovie> call, Response<TopRatedMovie> response) {
                   if(response.body()!=null)
                   {
                       TopRatedMovie topRatedResponse;
                       List<TopRatedMovie> topRateds=new ArrayList<>();
                       topRatedResponse=response.body();
                       movieDAO.insertMovies(topRatedResponse.getResults());
                       for(int i=0;i<topRatedResponse.getResults().size();i++)
                       {
                           TopRatedMovie topRated=new TopRatedMovie();
                           topRated.setMovieId(topRatedResponse.getResults().get(i).getId());
                           topRateds.add(topRated);
                       }
                       movieDAO.insertTopRatedMovies(topRateds);
                       movies.clear();
                       movies.addAll(topRatedResponse.getResults());
                       listener.onMoviesListLoaded(movies);
                   }
                }

                @Override
                public void onFailure(Call<TopRatedMovie> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.UPCOMING_MOVIES))
        {
            int[] upcomingIds;
            upcomingIds=movieDAO.getUpcomingMovie();
            movies.addAll(movieDAO.getNowUpMovies(upcomingIds));
            Call<UpcomingMovie> callUpcoming= ApiClient.getMovieDBService().getUpComingMovies(Constants.API_KEY,Page);
            callUpcoming.enqueue(new Callback<UpcomingMovie>() {
                @Override
                public void onResponse(Call<UpcomingMovie> call, Response<UpcomingMovie> response) {
                if(response.body()!=null)
                {
                    UpcomingMovie upcomingResponse;
                    List<UpcomingMovie> upcomings=new ArrayList<>();
                    upcomingResponse=response.body();
                    movieDAO.insertMovies(upcomingResponse.getResults());
                    for(int i=0;i<upcomingResponse.getResults().size();i++)
                    {
                        UpcomingMovie upcoming=new UpcomingMovie();
                        upcoming.setMovieId(upcomingResponse.getResults().get(i).getId());
                        upcomings.add(upcoming);
                    }
                    movieDAO.insertUpcomingMovies(upcomings);
                    movies.clear();
                    movies.addAll(upcomingResponse.getResults());
                    listener.onMoviesListLoaded(movies);
                }

                }

                @Override
                public void onFailure(Call<UpcomingMovie> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.SIMILAR_MOVIES))
        {
            Call<SimilarMovie> callSimilar=ApiClient.getMovieDBService().getSimilarMovies(id,Constants.API_KEY,Page);
            callSimilar.enqueue(new Callback<SimilarMovie>() {
                @Override
                public void onResponse(Call<SimilarMovie> call, Response<SimilarMovie> response) {
                    if (response.body()!=null)
                    {

                        SimilarMovie movie=response.body();
                        movies.addAll(movie.getResults());
                        listener.onMoviesListLoaded(movies);
                    }
                }

                @Override
                public void onFailure(Call<SimilarMovie> call, Throwable t) {

                }
            });
        }
        return movies;

    }
    public List<Shows> getShows(int Page,long id)
    {
        showDAO=database.getShowDAO();
        shows=new ArrayList<>();
        if(type.equals(Constants.AIR_TODAY_SHOWS))
        {
            int[] airTodayIds;
            airTodayIds=showDAO.getAirTodayShows();
            shows.addAll(showDAO.getAirShows(airTodayIds));
            Call<AirTodayShows> callAirToday= ApiClient.getMovieDBService().getAirTodayShows(Constants.API_KEY,Page);
            callAirToday.enqueue(new Callback<AirTodayShows>() {
                @Override
                public void onResponse(Call<AirTodayShows> call, Response<AirTodayShows> response) {
                   if(response.body()!=null)
                   {
                       AirTodayShows airTodayResponse;
                       List<AirTodayShows> airTodays=new ArrayList<>();
                       airTodayResponse=response.body();
                       showDAO.insertShows(airTodayResponse.getResults());
                       for(int i=0;i<airTodayResponse.getResults().size();i++)
                       {
                           AirTodayShows airToday=new AirTodayShows();
                           airToday.setShowId(airTodayResponse.getResults().get(i).getId());
                           airTodays.add(airToday);
                       }
                       showDAO.insertAirTodayShows(airTodays);
                       shows.clear();
                       shows.addAll(airTodayResponse.getResults());
                       listener.onShowsListLoaded(shows);
                   }
                }

                @Override
                public void onFailure(Call<AirTodayShows> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.POPULAR_SHOWS))
        {
            int[] popularIds;
            popularIds=showDAO.getPopularShows();
            shows.addAll(showDAO.getTopPopShows(popularIds));
            Call<PopularShows> callPopular= ApiClient.getMovieDBService().getPopularShows(Constants.API_KEY,Page);
            callPopular.enqueue(new Callback<PopularShows>() {
                @Override
                public void onResponse(Call<PopularShows> call, Response<PopularShows> response) {
                  if(response.body()!=null)
                  {
                      PopularShows popularResponse;
                      List<PopularShows> populars=new ArrayList<>();
                      popularResponse=response.body();
                      showDAO.insertShows(popularResponse.getResults());
                      for(int i=0;i<popularResponse.getResults().size();i++)
                      {
                          PopularShows popular=new PopularShows();
                          popular.setShowId(popularResponse.getResults().get(i).getId());
                          populars.add(popular);
                      }
                      showDAO.insertPopularShows(populars);
                      shows.clear();
                      shows.addAll(popularResponse.getResults());
                      listener.onShowsListLoaded(shows);
                  }
                }

                @Override
                public void onFailure(Call<PopularShows> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.TOP_RATED_SHOWS))
        {
            int[] topRatedIds;

            topRatedIds=showDAO.getTopRatedShows();
            shows.addAll(showDAO.getTopPopShows(topRatedIds));
            Call<TopRatedShows> callTopRated= ApiClient.getMovieDBService().getTopRatedShows(Constants.API_KEY,Page);
            callTopRated.enqueue(new Callback<TopRatedShows>() {
                @Override
                public void onResponse(Call<TopRatedShows> call, Response<TopRatedShows> response) {
                    if(response.body()!=null)
                    {
                        TopRatedShows topRatedResponse;
                        List<TopRatedShows> topRateds=new ArrayList<>();
                        topRatedResponse=response.body();
                        showDAO.insertShows(topRatedResponse.getResults());
                        for(int i=0;i<topRatedResponse.getResults().size();i++)
                        {
                            TopRatedShows topRated=new TopRatedShows();
                            topRated.setShowId( topRatedResponse.getResults().get(i).getId());
                            topRateds.add( topRated);
                        }
                        showDAO.insertTopRatedShows( topRateds);
                        shows.clear();
                        shows.addAll( topRatedResponse.getResults());
                        listener.onShowsListLoaded(shows);
                    }
                }

                @Override
                public void onFailure(Call<TopRatedShows> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.ON_AIR_SHOWS))
        {
            int[] onAirIds;
            onAirIds=showDAO.getAirTodayShows();
            shows.addAll(showDAO.getAirShows(onAirIds));
            Call<OnAirShows> callOnAir= ApiClient.getMovieDBService().getOnAirShows(Constants.API_KEY,Page);
            callOnAir.enqueue(new Callback<OnAirShows>() {
                @Override
                public void onResponse(Call<OnAirShows> call, Response<OnAirShows> response) {
                    if(response.body()!=null)
                    {
                        OnAirShows onAirResponse;
                        List<OnAirShows> onAirs=new ArrayList<>();
                        onAirResponse=response.body();
                        showDAO.insertShows(onAirResponse.getResults());
                        for(int i=0;i<onAirResponse.getResults().size();i++)
                        {
                            OnAirShows onAir=new OnAirShows();
                            onAir.setShowId(onAirResponse.getResults().get(i).getId());
                            onAirs.add(onAir);
                        }
                        showDAO.insertOnAirShows(onAirs);
                        shows.clear();
                        shows.addAll(onAirResponse.getResults());
                        listener.onShowsListLoaded(shows);
                    }
                }

                @Override
                public void onFailure(Call<OnAirShows> call, Throwable t) {

                }
            });
        }
        else if(type.equals(Constants.SIMILAR_SHOWS))
        {
            Call<SimilarShow> similarShowCall=ApiClient.getMovieDBService().getSimilarShows(id,Constants.API_KEY,Page);
            similarShowCall.enqueue(new Callback<SimilarShow>() {
                @Override
                public void onResponse(Call<SimilarShow> call, Response<SimilarShow> response) {
                    if(response.body()!=null)
                    {
                        SimilarShow show=response.body();
                        shows.addAll(show.getResults());
                        listener.onShowsListLoaded(shows);
                    }
                }

                @Override
                public void onFailure(Call<SimilarShow> call, Throwable t) {

                }
            });
        }
       return shows;
    }


}

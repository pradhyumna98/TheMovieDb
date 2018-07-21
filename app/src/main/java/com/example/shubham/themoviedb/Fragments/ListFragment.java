package com.example.shubham.themoviedb.Fragments;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shubham.themoviedb.Activities.MainActivity;
import com.example.shubham.themoviedb.Adapter.RowListener;

import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Adapter.ShowsTvAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
RecyclerView RV;
ProgressBar progressBar;
TextView tv;
List<Movie> movies=new ArrayList<>();
ShowMovieAdapter moviesAdapter;
List<Shows> shows=new ArrayList<>();
ShowsTvAdapter showsAdapter;
String type,fragment;
Bundle bundle;
MovieDAO movieDAO;
ShowDAO showDAO;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output=inflater.inflate(R.layout.fragment_list, container, false);
        RV=output.findViewById(R.id.RecyclerView);
        progressBar=output.findViewById(R.id.progressBar);
        tv=output.findViewById(R.id.textView);
        Database database= Room.databaseBuilder(getContext().getApplicationContext(),Database.class,"movie_db").allowMainThreadQueries().build();
        movieDAO=database.getMovieDAO();
        showDAO=database.getShowDAO();
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        bundle=getArguments();
        fragment=bundle.getString(Constants.FRAGMENT);
        type=bundle.getString(Constants.TYPE);
        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            if(type.equals(Constants.NOW_SHOWING_MOVIES))
            {
                int[] nowShowingIds;
                tv.setText("Now Showing");
                nowShowingIds=movieDAO.getNowShowing();
                movies=movieDAO.getNowUpMovies(nowShowingIds);
                Call<NowShowingMovie> callNowShowing= ApiClient.getMovieDBService().getNowShowingMovies(Constants.API_KEY);
                callNowShowing.enqueue(new Callback<NowShowingMovie>() {
                    @Override
                    public void onResponse(Call<NowShowingMovie> call, Response<NowShowingMovie> response) {
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
                        moviesAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<NowShowingMovie> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.POPULAR_MOVIES))
            {
                int[] popularIds;
                tv.setText("Popular");
                popularIds=movieDAO.getPopularMovie();
                movies=movieDAO.getTopPopMovies(popularIds);
                Call<PopularMovie> callPopular= ApiClient.getMovieDBService().getPopularMovies(Constants.API_KEY);
                callPopular.enqueue(new Callback<PopularMovie>() {
                    @Override
                    public void onResponse(Call<PopularMovie> call, Response<PopularMovie> response) {
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
                        moviesAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<PopularMovie> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.TOP_RATED_MOVIES))
            {
                int[] topRatedIds;
                tv.setText("Top Rated");
                topRatedIds=movieDAO.getTopRatedMovie();
                movies=movieDAO.getTopPopMovies(topRatedIds);
                Call<TopRatedMovie> callTopRated= ApiClient.getMovieDBService().getTopRatedMovies(Constants.API_KEY);
                callTopRated.enqueue(new Callback<TopRatedMovie>() {
                    @Override
                    public void onResponse(Call<TopRatedMovie> call, Response<TopRatedMovie> response) {
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
                        moviesAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<TopRatedMovie> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.UPCOMING_MOVIES))
            {
                int[] upcomingIds;
                tv.setText("Up Coming");
                upcomingIds=movieDAO.getUpcomingMovie();
                movies=movieDAO.getNowUpMovies(upcomingIds);
                Call<UpcomingMovie> callUpcoming= ApiClient.getMovieDBService().getUpComingMovies(Constants.API_KEY);
                callUpcoming.enqueue(new Callback<UpcomingMovie>() {
                    @Override
                    public void onResponse(Call<UpcomingMovie> call, Response<UpcomingMovie> response) {
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
                        moviesAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<UpcomingMovie> call, Throwable t) {

                    }
                });
            }
            if(movies.size()>0)
            {
                RV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            moviesAdapter=new ShowMovieAdapter(getContext(), movies, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {

                }
            });
            RV.setAdapter(moviesAdapter);
        }
        else if(fragment.equals(Constants.SHOWS_FRAGMENT))
        {
            if(type.equals(Constants.AIR_TODAY_SHOWS))
            {
                int[] airTodayIds;
                tv.setText("Air Today");
                airTodayIds=showDAO.getAirTodayShows();
                shows=showDAO.getAirShows(airTodayIds);
                Call<AirTodayShows> callAirToday= ApiClient.getMovieDBService().getAirTodayShows(Constants.API_KEY);
                callAirToday.enqueue(new Callback<AirTodayShows>() {
                    @Override
                    public void onResponse(Call<AirTodayShows> call, Response<AirTodayShows> response) {
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
                        showsAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<AirTodayShows> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.POPULAR_SHOWS))
            {
                int[] popularIds;
                tv.setText("Popular");
                popularIds=showDAO.getAirTodayShows();
                shows=showDAO.getTopPopShows(popularIds);
                Call<PopularShows> callPopular= ApiClient.getMovieDBService().getPopularShows(Constants.API_KEY);
                callPopular.enqueue(new Callback<PopularShows>() {
                    @Override
                    public void onResponse(Call<PopularShows> call, Response<PopularShows> response) {
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
                        showsAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<PopularShows> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.TOP_RATED_SHOWS))
            {
                int[] topRatedIds;
                tv.setText("TopRated");
                topRatedIds=showDAO.getTopRatedShows();
                shows=showDAO.getTopPopShows(topRatedIds);
                Call<TopRatedShows> callTopRated= ApiClient.getMovieDBService().getTopRatedShows(Constants.API_KEY);
                callTopRated.enqueue(new Callback<TopRatedShows>() {
                    @Override
                    public void onResponse(Call<TopRatedShows> call, Response<TopRatedShows> response) {
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
                        showsAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<TopRatedShows> call, Throwable t) {

                    }
                });
            }
            else if(type.equals(Constants.ON_AIR_SHOWS))
            {
                int[] onAirIds;
                tv.setText("On Air");
                onAirIds=showDAO.getAirTodayShows();
                shows=showDAO.getAirShows(onAirIds);
                Call<OnAirShows> callOnAir= ApiClient.getMovieDBService().getOnAirShows(Constants.API_KEY);
                callOnAir.enqueue(new Callback<OnAirShows>() {
                    @Override
                    public void onResponse(Call<OnAirShows> call, Response<OnAirShows> response) {
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
                        showsAdapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<OnAirShows> call, Throwable t) {

                    }
                });
            }
            if(shows.size()>0)
            {
                RV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            showsAdapter=new ShowsTvAdapter(getContext(), shows, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {

                }
            });
            RV.setAdapter(showsAdapter);
        }
        
        return output;
    }

}

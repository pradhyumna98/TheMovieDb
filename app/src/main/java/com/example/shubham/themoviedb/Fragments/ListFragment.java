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
import com.example.shubham.themoviedb.Adapter.MoviesRowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;

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
ShowMovieAdapter adapter;
String type;
Bundle bundle;
MovieDAO movieDAO;

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
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        RV.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        RV.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        bundle=getArguments();
        type=bundle.getString(Constants.TYPE);
        if(type==Constants.NOW_SHOWING_MOVIES)
        {
            int[] nowShowingIds;
            tv.setText("Now Showing");
            nowShowingIds=movieDAO.getNowShowing();
            movies=movieDAO.getNowUpMovies(nowShowingIds);
            Call<NowShowingMovie> callNowShowing= ApiClient.getMovieDBService().getNowShowingMovies(MainActivity.API_KEY);
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
                    adapter.notifyDataSetChanged();
                    RV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<NowShowingMovie> call, Throwable t) {

                }
            });
        }
        else if(type==Constants.POPULAR_MOVIES)
        {
            int[] popularIds;
            tv.setText("Popular");
            popularIds=movieDAO.getPopularMovie();
            movies=movieDAO.getTopPopMovies(popularIds);
            Call<PopularMovie> callPopular= ApiClient.getMovieDBService().getPopularMovies(MainActivity.API_KEY);
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
                    adapter.notifyDataSetChanged();
                    RV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<PopularMovie> call, Throwable t) {

                }
            });
        }
        else if(type==Constants.TOP_RATED_MOVIES)
        {
            int[] topRatedIds;
            tv.setText("Top Rated");
            topRatedIds=movieDAO.getTopRatedMovie();
            movies=movieDAO.getTopPopMovies(topRatedIds);
            Call<TopRatedMovie> callTopRated= ApiClient.getMovieDBService().getTopRatedMovies(MainActivity.API_KEY);
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
                    adapter.notifyDataSetChanged();
                    RV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<TopRatedMovie> call, Throwable t) {

                }
            });
        }
        else if(type==Constants.UPCOMING_MOVIES)
        {
            int[] upcomingIds;
            tv.setText("Up Coming");
            upcomingIds=movieDAO.getUpcomingMovie();
            movies=movieDAO.getNowUpMovies(upcomingIds);
            Call<UpcomingMovie> callUpcoming= ApiClient.getMovieDBService().getUpComingMovies(MainActivity.API_KEY);
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
                    adapter.notifyDataSetChanged();
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
        adapter=new ShowMovieAdapter(getContext(), movies, new MoviesRowListener() {
            @Override
            public void onDownloadMoviesList(View view, int position) {

            }
        });
        RV.setAdapter(adapter);
        return output;
    }

}

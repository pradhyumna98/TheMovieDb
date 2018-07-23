package com.example.shubham.themoviedb.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;

import com.example.shubham.themoviedb.Adapter.RowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Adapter.ShowsTvAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.EndlessRecyclerViewScrollListener;
import com.example.shubham.themoviedb.ListLoadListener;
import com.example.shubham.themoviedb.LoadList;
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

public class GridActivity extends AppCompatActivity implements ListLoadListener {
RecyclerView RV;
LoadList listLoader;
String fragment;
ShowMovieAdapter moviesAdapter;
ShowsTvAdapter showsAdapter;
    List<Movie> movies=new ArrayList<>();
    List<Shows> shows=new ArrayList<>();
int page=1;
Bundle bundle=new Bundle();
Database database;
MovieDAO movieDAO;
ShowDAO showDAO;
    EndlessRecyclerViewScrollListener listener;
    GridLayoutManager manager;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        RV=findViewById(R.id.recyclerGrid);
        manager=new GridLayoutManager(this,3);
        RV.setLayoutManager(manager);
        database=Database.getInstance(this);
        movieDAO=database.getMovieDAO();
        showDAO=database.getShowDAO();
        Intent intent=getIntent();
        bundle=intent.getExtras();
        listLoader=new LoadList(bundle,this,this);
        fragment=bundle.getString(Constants.FRAGMENT);
        listener=new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                LoadList loadList=new LoadList(bundle, GridActivity.this, new ListLoadListener() {
                    @Override
                    public void onMoviesListLoaded(List<Movie> movies) {
                        GridActivity.this.movies.addAll(movies);
                        movieDAO.deleteMovies(movies);
                        moviesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onShowsListLoaded(List<Shows> shows) {
                        GridActivity.this.shows.addAll(shows);
                        showDAO.deleteShows(shows);
                        showsAdapter.notifyDataSetChanged();
                    }
                });
                if(fragment.equals(Constants.MOVIES_FRAGMENT))
                    loadList.getMovies(page);
                else if(fragment.equals(Constants.SHOWS_FRAGMENT))
                    loadList.getShows(page);
            }
        };
        RV.addOnScrollListener(listener);
        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            movies.addAll(listLoader.getMovies(page));
           moviesAdapter=new ShowMovieAdapter(this,movies, new RowListener() {
               @Override
               public void onListItemClicked(View view, int position) {

               }
           });
           RV.setAdapter(moviesAdapter);
        }
        else if(fragment.equals(Constants.SHOWS_FRAGMENT))
        {
            shows.addAll(listLoader.getShows(page));
            showsAdapter=new ShowsTvAdapter(this,shows, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {

                }
            });
            RV.setAdapter(showsAdapter);
        }
    }

    @Override
    public void onMoviesListLoaded(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();

    }

    @Override
    public void onShowsListLoaded(List<Shows> shows) {
        this.shows.clear();
        this.shows.addAll(shows);
        showsAdapter.notifyDataSetChanged();
    }
}

package com.example.shubham.themoviedb.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.shubham.themoviedb.Fragments.GridFragment;
import com.example.shubham.themoviedb.ListLoadListener;
import com.example.shubham.themoviedb.LoadList;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.FavouriteMovies;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.FavouriteShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridActivity extends AppCompatActivity {
GridFragment fragment=new GridFragment();
Bundle bundle=new Bundle();
@Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        bundle=getIntent().getExtras();
        fragment.setArguments(bundle);
        transaction.replace(R.id.recyclerGridContainer,fragment);
        transaction.commit();
    }

}

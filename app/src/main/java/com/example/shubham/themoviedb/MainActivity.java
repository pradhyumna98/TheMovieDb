package com.example.shubham.themoviedb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
RecyclerView nowShowingRV,topRatedRV,upComingRV;
UpcomingMovie upcomingResponse;
NowShowingMovie nowShowingResponse;
ArrayList<Movie> upcomingMovies=new ArrayList<>();
ArrayList<Movie> nowShowingMovies=new ArrayList<>();
ShowMovieAdapter upComingAdapter,nowShowingAdapter;
ProgressBar progressBarUpcoming,progressBarNowShowing,progressBarTopRated;
public static final String API_KEY="52d58450e4782fc69aef2ff928bb2162";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nowShowingRV=findViewById(R.id.NowShowing);
        topRatedRV=findViewById(R.id.TopRated);
        upComingRV=findViewById(R.id.UpComing);
        progressBarUpcoming=findViewById(R.id.progressBarUpComing);
        progressBarNowShowing=findViewById(R.id.progressBarNowShowing);
        progressBarTopRated=findViewById(R.id.progressBarTopRated);
        nowShowingAdapter=new ShowMovieAdapter(this, nowShowingMovies, new MoviesRowListener() {
            @Override
            public void onDownloadMoviesList(View view, int position) {

            }
        });
        upComingAdapter=new ShowMovieAdapter(this, upcomingMovies, new MoviesRowListener() {
            @Override
            public void onDownloadMoviesList(View view, int position) {

            }
        });
        upComingRV.setAdapter(upComingAdapter);
        upComingRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        upComingRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        upComingRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        nowShowingRV.setAdapter(nowShowingAdapter);
        nowShowingRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        nowShowingRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        nowShowingRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        Call<UpcomingMovie> callUpcoming=ApiClient.getMovieDBService().getUpComingMovies(API_KEY);
        callUpcoming.enqueue(new Callback<UpcomingMovie>() {
            @Override
            public void onResponse(Call<UpcomingMovie> call, Response<UpcomingMovie> response) {
                upcomingResponse=response.body();
                upcomingMovies.clear();
                upcomingMovies.addAll(upcomingResponse.results);
                upComingAdapter.notifyDataSetChanged();
                upComingRV.setVisibility(View.VISIBLE);
                progressBarUpcoming.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<UpcomingMovie> call, Throwable t) {

            }
        });
        Call<NowShowingMovie> callNowShowing=ApiClient.getMovieDBService().getNowShowingMovies(API_KEY);
        callNowShowing.enqueue(new Callback<NowShowingMovie>() {
            @Override
            public void onResponse(Call<NowShowingMovie> call, Response<NowShowingMovie> response) {
                nowShowingResponse=response.body();
                nowShowingMovies.clear();
                nowShowingMovies.addAll(nowShowingResponse.results);
                nowShowingAdapter.notifyDataSetChanged();
                nowShowingRV.setVisibility(View.VISIBLE);
                progressBarNowShowing.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<NowShowingMovie> call, Throwable t) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

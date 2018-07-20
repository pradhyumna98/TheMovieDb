package com.example.shubham.themoviedb.Activities;

import android.arch.persistence.room.Room;
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

import com.example.shubham.themoviedb.Adapter.MoviesRowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movie;
import com.example.shubham.themoviedb.entities.NowShowingMovie;
import com.example.shubham.themoviedb.entities.TopRatedMovie;
import com.example.shubham.themoviedb.entities.UpcomingMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RecyclerView nowShowingRV,topRatedRV,upComingRV;
UpcomingMovie upcomingResponse;
NowShowingMovie nowShowingResponse;
TopRatedMovie topRatedResponse;
int[] upcomingIds,nowShowingIds,topRatedIds;
List<UpcomingMovie> upcomings=new ArrayList<>();
List<NowShowingMovie> nowShowings=new ArrayList<>();
List<TopRatedMovie> topRateds=new ArrayList<>();
List<Movie> upcomingMovies=new ArrayList<>();
List<Movie> nowShowingMovies=new ArrayList<>();
List<Movie> topRatedMovies=new ArrayList<>();
ShowMovieAdapter upComingAdapter,nowShowingAdapter,topRatedAdapter;
ProgressBar progressBarUpcoming,progressBarNowShowing,progressBarTopRated;
MovieDAO movieDAO;
public static final String API_KEY="52d58450e4782fc69aef2ff928bb2162";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Database database= Room.databaseBuilder(getApplicationContext(),Database.class,"movie_db").allowMainThreadQueries().build();
        movieDAO=database.getMovieDAO();
        upcomingIds=movieDAO.getUpcomingMovie();
        nowShowingIds=movieDAO.getNowShowing();
        topRatedIds=movieDAO.getTopRatedMovie();
        upcomingMovies=movieDAO.getMovies(upcomingIds);
        nowShowingMovies=movieDAO.getMovies(nowShowingIds);
        topRatedMovies=movieDAO.getMovies(topRatedIds);
        nowShowingRV=findViewById(R.id.NowShowing);
        topRatedRV=findViewById(R.id.TopRated);
        upComingRV=findViewById(R.id.UpComing);
        progressBarUpcoming=findViewById(R.id.progressBarUpComing);
        progressBarNowShowing=findViewById(R.id.progressBarNowShowing);
        progressBarTopRated=findViewById(R.id.progressBarTopRated);
        if(upcomingMovies.size()>0)
        {
            progressBarUpcoming.setVisibility(View.INVISIBLE);
            upComingRV.setVisibility(View.VISIBLE);
        }
        if(nowShowingMovies.size()>0)
        {
            progressBarNowShowing.setVisibility(View.INVISIBLE);
            nowShowingRV.setVisibility(View.VISIBLE);
        }
        if(topRatedMovies.size()>0)
        {
            progressBarTopRated.setVisibility(View.INVISIBLE);
            topRatedRV.setVisibility(View.VISIBLE);
        }
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
        topRatedAdapter=new ShowMovieAdapter(this, topRatedMovies, new MoviesRowListener() {
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
        topRatedRV.setAdapter(topRatedAdapter);
        topRatedRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        topRatedRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        topRatedRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        Call<UpcomingMovie> callUpcoming= ApiClient.getMovieDBService().getUpComingMovies(API_KEY);
        callUpcoming.enqueue(new Callback<UpcomingMovie>() {
            @Override
            public void onResponse(Call<UpcomingMovie> call, Response<UpcomingMovie> response) {
                upcomingResponse=response.body();
                movieDAO.insertMovies(upcomingResponse.getResults());
                for(int i=0;i<upcomingResponse.getResults().size();i++)
                {
                    UpcomingMovie upComing=new UpcomingMovie();
                    upComing.setMovieId(upcomingResponse.getResults().get(i).getId());
                    upcomings.add(upComing);
                }
                movieDAO.insertUpcomingMovies(upcomings);
                upcomingMovies.clear();
                upcomingMovies.addAll(upcomingResponse.getResults());
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
                movieDAO.insertMovies(nowShowingResponse.getResults());
                for(int i=0;i<nowShowingResponse.getResults().size();i++)
                {
                    NowShowingMovie nowShowing=new NowShowingMovie();
                    nowShowing.setMovieId(nowShowingResponse.getResults().get(i).getId());
                    nowShowings.add(nowShowing);
                }
                movieDAO.insertNowShowingMovies(nowShowings);
                nowShowingMovies.clear();
                nowShowingMovies.addAll(nowShowingResponse.getResults());
                nowShowingAdapter.notifyDataSetChanged();
                nowShowingRV.setVisibility(View.VISIBLE);
                progressBarNowShowing.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<NowShowingMovie> call, Throwable t) {

            }
        });
        Call<TopRatedMovie> callTopRated=ApiClient.getMovieDBService().getTopRatedMovies(API_KEY);
        callTopRated.enqueue(new Callback<TopRatedMovie>() {
            @Override
            public void onResponse(Call<TopRatedMovie> call, Response<TopRatedMovie> response) {
                topRatedResponse=response.body();
                movieDAO.insertMovies(topRatedResponse.getResults());
                for(int i=0;i<topRatedResponse.getResults().size();i++)
                {
                    TopRatedMovie topRated=new TopRatedMovie();
                    topRated.setMovieId(topRatedResponse.getResults().get(i).getId());
                    topRateds.add(topRated);
                }
                movieDAO.insertTopRatedMovies(topRateds);
                topRatedMovies.clear();
                topRatedMovies.addAll(topRatedResponse.getResults());
                topRatedAdapter.notifyDataSetChanged();
                topRatedRV.setVisibility(View.VISIBLE);
                progressBarTopRated.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<TopRatedMovie> call, Throwable t) {

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

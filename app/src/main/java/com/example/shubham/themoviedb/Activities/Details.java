package com.example.shubham.themoviedb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shubham.themoviedb.Adapter.CastAdapter;
import com.example.shubham.themoviedb.Adapter.CastRowListener;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Fragments.ListFragment;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Credits;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {
ImageView ivPoster,ivBackDrop;
TextView tvName,tvGenre;
ExpandableTextView tvOverView;
RecyclerView recyclerView;
CastAdapter adapter;
List<People> peoples=new ArrayList<>();
CollapsingToolbarLayout collapsingToolbarLayout;
ProgressBar progressBar;
String posterPath,backDropPath,name,genre,fragment;
ListFragment listFragment=new ListFragment();
FragmentManager manager;
Bundle bundle=new Bundle();
Movie movie;
Shows show;
long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ivPoster=findViewById(R.id.poster);
        ivBackDrop=findViewById(R.id.backdrop);
        tvGenre=findViewById(R.id.textView3);
        recyclerView=findViewById(R.id.recyclerViewCast);
        adapter=new CastAdapter(this, peoples, new CastRowListener() {
            @Override
            public void onCastListItemClicked(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        progressBar=findViewById(R.id.progressBarCast);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        collapsingToolbarLayout=findViewById(R.id.toolbar_layout);
        tvOverView=findViewById(R.id.expand_text_view);
        Intent intent=getIntent();
        fragment=intent.getStringExtra(Constants.FRAGMENT);
        id=intent.getLongExtra(Constants.ID,0);
        manager=getSupportFragmentManager();

        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            Call<Credits> creditsCall=ApiClient.getMovieDBService().getMovieCreditDetails(id,Constants.API_KEY);
            creditsCall.enqueue(new Callback<Credits>() {
                @Override
                public void onResponse(Call<Credits> call, Response<Credits> response) {
                    peoples.clear();
                    peoples.addAll(response.body().getCast());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Credits> call, Throwable t) {

                }
            });
            Call<Movie> movieCall= ApiClient.getMovieDBService().getMovieDetails(id,Constants.API_KEY);
            movieCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    movie =response.body();
                    collapsingToolbarLayout.setTitle(movie.getTitle());
                    tvOverView.setText(movie.getOverview());
                    Picasso.get().load(Constants.IMAGE_URL+movie.getPosterPath()).fit().into(ivPoster);
                    Picasso.get().load(Constants.IMAGE_URL780+movie.getBackdropPath()).fit().into(ivBackDrop);
                    if(!(movie.getGenres().size()<=0))
                    {
                        tvGenre.setText(movie.getGenres().get(0).getName());
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }
            });
            bundle.putString(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
            bundle.putString(Constants.TYPE,Constants.SIMILAR_MOVIES);
            bundle.putLong(Constants.ID,id);
            FragmentTransaction transaction=manager.beginTransaction();
            listFragment.setArguments(bundle);
            transaction.replace(R.id.SimilarContainer,listFragment);
            transaction.commit();
        }
        else if(fragment.equals(Constants.SHOWS_FRAGMENT))
        {
            Call<Credits> creditsCall=ApiClient.getMovieDBService().getShowCreditDetails(id,Constants.API_KEY);
            creditsCall.enqueue(new Callback<Credits>() {
                @Override
                public void onResponse(Call<Credits> call, Response<Credits> response) {
                    peoples.clear();
                    peoples.addAll(response.body().getCast());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Credits> call, Throwable t) {

                }
            });
            Call<Shows> showsCall=ApiClient.getMovieDBService().getShowDetails(id,Constants.API_KEY);
            showsCall.enqueue(new Callback<Shows>() {
                @Override
                public void onResponse(Call<Shows> call, Response<Shows> response) {
                    show=response.body();
                    collapsingToolbarLayout.setTitle(show.getName());
                    tvOverView.setText(show.getOverview());
                    Picasso.get().load(Constants.IMAGE_URL+show.getPosterPath()).fit().into(ivPoster);
                    Picasso.get().load(Constants.IMAGE_URL780+show.getBackdropPath()).fit().into(ivBackDrop);
                    if(!(show.getGenres().size()<=0))
                    {
                        tvGenre.setText(show.getGenres().get(0).getName());
                    }
                }

                @Override
                public void onFailure(Call<Shows> call, Throwable t) {

                }
            });
            bundle.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
            bundle.putString(Constants.TYPE,Constants.SIMILAR_SHOWS);
            bundle.putLong(Constants.ID,id);
            FragmentTransaction transaction=manager.beginTransaction();
            listFragment.setArguments(bundle);
            transaction.replace(R.id.SimilarContainer,listFragment);
            transaction.commit();
        }

    }
}

package com.example.shubham.themoviedb.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.shubham.themoviedb.Adapter.CastAdapter;
import com.example.shubham.themoviedb.Adapter.CastRowListener;
import com.example.shubham.themoviedb.Adapter.VideoAdapter;
import com.example.shubham.themoviedb.Adapter.VideoListener;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.Fragments.ListFragment;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Credits;
import com.example.shubham.themoviedb.entities.Movies.FavouriteMovies;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.TvShows.FavouriteShows;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.VideoResult;
import com.example.shubham.themoviedb.entities.Video;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {
ImageView ivPoster,ivBackDrop;
TextView tvStar,tvGenre;
ToggleButton toggleButton;
ExpandableTextView tvOverView;
RecyclerView recyclerView,recyclerView1;
VideoAdapter adapter1;
CastAdapter adapter;
List<People> peoples=new ArrayList<>();
List<Video> videos=new ArrayList<>();
CollapsingToolbarLayout collapsingToolbarLayout;
ProgressBar progressBar,progressBar1;
String fragment;
ListFragment listFragment=new ListFragment();
FragmentManager manager;
Bundle bundle=new Bundle();
Database database;
MovieDAO movieDAO;
ShowDAO showDAO;
Movie movie;
Shows show;
long id;
int[] favouriteIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ivPoster=findViewById(R.id.poster);
        ivBackDrop=findViewById(R.id.backdrop);
        tvGenre=findViewById(R.id.textView3);
        toggleButton=findViewById(R.id.toggleButton);
        tvStar=findViewById(R.id.textstar);
        recyclerView1=findViewById(R.id.recyclerViewVideos);
        progressBar1=findViewById(R.id.progressBarVideos);
        database=Database.getInstance(this);
        movieDAO=database.getMovieDAO();
        showDAO=database.getShowDAO();
        recyclerView=findViewById(R.id.recyclerViewCast);
        adapter=new CastAdapter(this, peoples, new CastRowListener() {
            @Override
            public void onCastListItemClicked(View view, int position) {
                Intent intent=new Intent(Details.this, CastDetailsActivity.class);
                intent.putExtra(Constants.ID,peoples.get(position).getId());
                startActivity(intent);
            }
        });
        adapter1=new VideoAdapter(this, videos, new VideoListener() {
            @Override
            public void onListItemClicked(int position, View view,Video video) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" +video.getKey() ));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + id));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });
        recyclerView1.setAdapter(adapter1);
        recyclerView.setAdapter(adapter);
        progressBar=findViewById(R.id.progressBarCast);
        LinearSnapHelper snapHelper=new LinearSnapHelper(),snapHelper1=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        snapHelper1.attachToRecyclerView(recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        collapsingToolbarLayout=findViewById(R.id.toolbar_layout);
        tvOverView=findViewById(R.id.expand_text_view);
        Intent intent=getIntent();
        fragment=intent.getStringExtra(Constants.FRAGMENT);
        id=intent.getLongExtra(Constants.ID,0);

        manager=getSupportFragmentManager();
        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            Boolean flag=false;
            favouriteIds=movieDAO.getFavouriteMovies();
            for (int i = 0; i < favouriteIds.length; i++) {
                int favouriteId = favouriteIds[i];
                if(favouriteId==id)
                    flag=true;
            }
            if(flag)
            {
                toggleButton.setChecked(true);
            }
            else
            {
                toggleButton.setChecked(false);
            }

            Call<Movie> movieCall= ApiClient.getMovieDBService().getMovieDetails(id,Constants.API_KEY,Constants.APPEND);
            movieCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    movie =response.body();
                    collapsingToolbarLayout.setTitle(movie.getTitle());
                    tvOverView.setText(movie.getOverview()+"\n\nRelease Date: "+movie.getReleaseDate());
                    tvStar.setText(movie.getVoteAverage()+tvStar.getText().toString());
                    Picasso.get().load(Constants.IMAGE_URL+movie.getPosterPath()).fit().into(ivPoster);
                    Picasso.get().load(Constants.IMAGE_URL780+movie.getBackdropPath()).fit().into(ivBackDrop);
                    if(!(movie.getGenres().size()<=0))
                    {
                        tvGenre.setText(movie.getGenres().get(0).getName());
                    }
                    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            ScaleAnimation scaleAnimation;
                            BounceInterpolator bounceInterpolator;
                            scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
                            scaleAnimation.setDuration(500);
                            bounceInterpolator = new BounceInterpolator();
                            scaleAnimation.setInterpolator(bounceInterpolator);
                            buttonView.startAnimation(scaleAnimation);
                            FavouriteMovies favouriteMovies=new FavouriteMovies();
                            favouriteMovies.setMovieId(movie.getId());
                            if(isChecked)
                            {
                                movieDAO.insertMovie(movie);
                                movieDAO.insertFavouriteMovie(favouriteMovies);
                            }
                            else {
                                movieDAO.deleteFavouriteMovies(favouriteMovies.getMovieId());
                            }

                        }
                    });
                    videos.clear();
                    videos.addAll(response.body().getVideos().getResults());
//                    if(response.body().getResults().size()>0){
//                    for (int i = 0; i <response.body().getResults().size(); i++) {
//                    Video video=response.body().getResults().get(i);
//                    if(video.getSite()=="YouTube")
//                    videos.add(video);
//                    }}
                    adapter1.notifyDataSetChanged();
                    recyclerView1.setVisibility(View.VISIBLE);
                    progressBar1.setVisibility(View.INVISIBLE);
                    peoples.clear();
                    peoples.addAll(response.body().getCredits().getCast());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
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
            Boolean flag=false;
            favouriteIds=showDAO.getFavouriteShows();
            for (int i = 0; i < favouriteIds.length; i++) {
                int favouriteId = favouriteIds[i];
                if(favouriteId==id)
                    flag=true;
            }
            if(flag)
            {
                toggleButton.setChecked(true);
            }
            else
            {
                toggleButton.setChecked(false);
            }
            Call<Shows> showsCall=ApiClient.getMovieDBService().getShowDetails(id,Constants.API_KEY,Constants.APPEND);
            showsCall.enqueue(new Callback<Shows>() {
                @Override
                public void onResponse(Call<Shows> call, Response<Shows> response) {
                    show=response.body();
                    collapsingToolbarLayout.setTitle(show.getName());
                    tvStar.setText(show.getVoteAverage()+tvStar.getText().toString());
                    tvOverView.setText(show.getOverview()+"\n\nRelease Date: "+show.getFirstAirDate());
                    Picasso.get().load(Constants.IMAGE_URL+show.getPosterPath()).fit().into(ivPoster);
                    Picasso.get().load(Constants.IMAGE_URL780+show.getBackdropPath()).fit().into(ivBackDrop);
                    if(!(show.getGenres().size()<=0))
                    {
                        tvGenre.setText(show.getGenres().get(0).getName());
                    }
                    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            ScaleAnimation scaleAnimation;
                            BounceInterpolator bounceInterpolator;
                            scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
                            scaleAnimation.setDuration(500);
                            bounceInterpolator = new BounceInterpolator();
                            scaleAnimation.setInterpolator(bounceInterpolator);
                            buttonView.startAnimation(scaleAnimation);
                            FavouriteShows favouriteShows=new FavouriteShows();
                            favouriteShows.setShowId(show.getId());
                            if(isChecked)
                            {
                                showDAO.insertShow(show);
                                showDAO.insertFavouriteShow(favouriteShows);
                            }
                            else {
                                showDAO.deleteFavouriteShows(favouriteShows.getShowId());
                            }
                        }
                    });
                    videos.clear();
                    videos.addAll(response.body().getVideos().getResults());
//                    if(response.body().getResults().size()>0){
//                        for (int i = 0; i <response.body().getResults().size(); i++) {
//                            Video video=response.body().getResults().get(i);
//                            if(video.getSite()=="YouTube")
//                                videos.add(video);
//                        }}
                    adapter1.notifyDataSetChanged();
                    recyclerView1.setVisibility(View.VISIBLE);
                    progressBar1.setVisibility(View.INVISIBLE);
                    peoples.clear();
                    peoples.addAll(response.body().getCredits().getCast());
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
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

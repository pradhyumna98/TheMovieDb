package com.example.shubham.themoviedb.Activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Fragments.ListFragment;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDetailsActivity extends AppCompatActivity {
    ImageView ivPoster;
    TextView tvAge,tvBirthPlace;
    ExpandableTextView tvOverView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    People people;
    long id;
ListFragment fragmentMovie=new ListFragment(),fragmentShow=new ListFragment();
Bundle bundle=new Bundle(),bundle2=new Bundle();
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        ivPoster=findViewById(R.id.profile_image1);
        tvAge=findViewById(R.id.textAge);
        tvOverView=findViewById(R.id.expand_text_view);
        tvBirthPlace=findViewById(R.id.textBirthplace);
        collapsingToolbarLayout=findViewById(R.id.toolbar_layout);
        id=getIntent().getLongExtra(Constants.ID,0);
        bundle.putString(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
        bundle.putString(Constants.TYPE,Constants.MOVIE_CREDITS);
        bundle.putLong(Constants.ID,id);
        bundle2.putLong(Constants.ID,id);
        bundle2.putString(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
        bundle2.putString(Constants.TYPE,Constants.SHOWS_CREDITS);
        fragmentMovie.setArguments(bundle);
        fragmentShow.setArguments(bundle2);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.MoviesCastContainer,fragmentMovie);
        transaction.add(R.id.ShowsCastContainer,fragmentShow);
        transaction.commit();
        Call<People> call= ApiClient.getMovieDBService().getPeopleDetails(id,Constants.API_KEY);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                people=response.body();
                collapsingToolbarLayout.setTitle(people.getName());
                Picasso.get().load(Constants.IMAGE_URL+people.getProfilePath()).fit().into(ivPoster);
                tvAge.setText("Birth Date: "+people.getBirthday());
                tvBirthPlace.setText("Birthplace: "+people.getPlaceOfBirth());
                tvOverView.setText(people.getBiography());
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }
}

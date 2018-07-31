package com.example.shubham.themoviedb.Activities;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shubham.themoviedb.Adapter.PageAdapter;
import com.example.shubham.themoviedb.Adapter.PageFavouriteAdapter;
import com.example.shubham.themoviedb.Fragments.FavouriteFragment;
import com.example.shubham.themoviedb.R;


public class Favourite extends AppCompatActivity implements FavouriteFragment.OnFragmentInteractionListener{

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        final Toolbar toolbar = findViewById(R.id.toolbarFavourite);
        setSupportActionBar(toolbar);
        tabLayout=findViewById(R.id.tablayoutFavourite);
        viewPager = findViewById(R.id.favouriteContainer);
        PageFavouriteAdapter adapter = new PageFavouriteAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        }






    @Override
    public void onFragmentInteraction() {

    }
}

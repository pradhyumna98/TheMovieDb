package com.example.shubham.themoviedb.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Fragments.FavouriteFragment;
import com.example.shubham.themoviedb.Fragments.MoviesFragment;
import com.example.shubham.themoviedb.Fragments.ShowsFragment;

/**
 * Created by shubham on 7/30/2018.
 */

public class PageFavouriteAdapter extends FragmentPagerAdapter {
    public PageFavouriteAdapter(FragmentManager fm) {
        super(fm);
    }
    FavouriteFragment fragmentMovies,fragmentShows;
    Bundle bundle;
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            if(fragmentMovies==null)
            {
                fragmentMovies=new FavouriteFragment();
            }
            bundle=new Bundle();
            bundle.putString(Constants.FRAGMENT,Constants.FAVOURITE_FRAGMENT);
            bundle.putString(Constants.TYPE,Constants.FAVOURIT_MOVIES);
            fragmentMovies.setArguments(bundle);
            return fragmentMovies;
        }
        else
        {
            if(fragmentShows==null)
            {
                fragmentShows=new FavouriteFragment();
            }
            bundle=new Bundle();
            bundle.putString(Constants.FRAGMENT,Constants.FAVOURITE_FRAGMENT);
            bundle.putString(Constants.TYPE,Constants.FAVOURITE_SHOWS);
            fragmentShows.setArguments(bundle);
            return fragmentShows;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}

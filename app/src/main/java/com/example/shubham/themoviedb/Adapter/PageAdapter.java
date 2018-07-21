package com.example.shubham.themoviedb.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shubham.themoviedb.Fragments.MoviesFragment;
import com.example.shubham.themoviedb.Fragments.ShowsFragment;

/**
 * Created by shubham on 7/21/2018.
 */

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }
    MoviesFragment moviesFragment;
    ShowsFragment showsFragment;
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            if(moviesFragment==null)
            {
                moviesFragment=new MoviesFragment();
            }
            return moviesFragment;
        }
        else
        {
            if(showsFragment==null)
            {
                showsFragment=new ShowsFragment();
            }
            return showsFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

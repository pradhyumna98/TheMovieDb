package com.example.shubham.themoviedb.Adapter;

import android.view.View;

import com.example.shubham.themoviedb.entities.SearchItems;

/**
 * Created by shubham on 7/15/2018.
 */

public interface RowListener {
    void onListItemClicked(View view,int position);
    void onButtonClicked(int position, Boolean checked, SearchItems items);
}

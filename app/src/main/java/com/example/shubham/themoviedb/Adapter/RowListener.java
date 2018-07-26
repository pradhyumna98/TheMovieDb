package com.example.shubham.themoviedb.Adapter;

import android.view.View;

/**
 * Created by shubham on 7/15/2018.
 */

public interface RowListener {
    void onListItemClicked(View view,int position);
    void onButtonClicked(int position,Boolean checked);
}

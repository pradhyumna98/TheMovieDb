package com.example.shubham.themoviedb.Adapter;

import android.view.View;

import com.example.shubham.themoviedb.entities.Video;

/**
 * Created by shubham on 7/30/2018.
 */

public interface VideoListener {
public void onListItemClicked(int position, View view, Video video);
}

package com.example.shubham.themoviedb.entities;

import com.example.shubham.themoviedb.entities.Video;

import java.util.ArrayList;

/**
 * Created by shubham on 7/30/2018.
 */

public class VideoResult {
    ArrayList<Video> results;

    public ArrayList<Video> getResults() {
        return results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }
}

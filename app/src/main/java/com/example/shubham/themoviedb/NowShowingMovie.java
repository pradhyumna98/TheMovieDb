package com.example.shubham.themoviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham on 7/15/2018.
 */

public class NowShowingMovie {
    int page;
    ArrayList<Movie> results;
    Dates dates;
    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalResults;
}

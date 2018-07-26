package com.example.shubham.themoviedb.entities.TvShows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham on 7/25/2018.
 */

public class SimilarShow {


    int page;
    ArrayList<Shows> results;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Shows> getResults() {
        return results;
    }

    public void setResults(ArrayList<Shows> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}


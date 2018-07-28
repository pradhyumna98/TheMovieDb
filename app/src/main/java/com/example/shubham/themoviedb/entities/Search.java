package com.example.shubham.themoviedb.entities;

import android.arch.persistence.room.Ignore;

import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shubham on 7/27/2018.
 */

public class Search {
    int page;
    List<SearchItems> results;
    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<SearchItems> getResults() {
        return results;
    }

    public void setResults(List<SearchItems> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}

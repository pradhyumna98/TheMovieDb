package com.example.shubham.themoviedb.entities.Movies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.shubham.themoviedb.Database.Dates;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shubham on 7/15/2018.
 */
@Entity(foreignKeys =
        {@ForeignKey(entity = Movie.class,parentColumns ="id" ,childColumns = "movieId")})
public class UpcomingMovie {
    @PrimaryKey(autoGenerate = true)
    int id;
    int movieId;
    @Ignore
    int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
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

    @Ignore
    List<Movie> results;
    @Ignore
    Dates dates;
    @Ignore
    @SerializedName("total_pages")
    int totalPages;
    @Ignore
    @SerializedName("total_results")
    int totalResults;
}

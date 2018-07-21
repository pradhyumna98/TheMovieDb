package com.example.shubham.themoviedb.entities.TvShows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham on 7/21/2018.
 */
@Entity(foreignKeys={@ForeignKey(entity = Shows.class,parentColumns = "id",childColumns = "showId",onUpdate = ForeignKey.CASCADE)})
public class OnAirShows {
    @PrimaryKey(autoGenerate = true)
    int id;
    long showId;
    @Ignore
    int page;
    @Ignore
    ArrayList<Shows> results;
    @Ignore
    @SerializedName("total_results")
    int totalResults;
    @Ignore
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getShowId() {
        return showId;
    }

    public void setShowId(long showId) {
        this.showId = showId;
    }
}

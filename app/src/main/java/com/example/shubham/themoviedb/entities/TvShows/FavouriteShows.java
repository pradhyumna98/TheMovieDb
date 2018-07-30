package com.example.shubham.themoviedb.entities.TvShows;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.shubham.themoviedb.entities.Movies.Movie;

/**
 * Created by shubham on 7/29/2018.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Shows.class,parentColumns = "id",childColumns = "showId",onDelete = ForeignKey.CASCADE)})
public class FavouriteShows
{
    @PrimaryKey(autoGenerate = true)
    int id;
    long showId;

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

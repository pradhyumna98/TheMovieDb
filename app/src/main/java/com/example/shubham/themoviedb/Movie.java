package com.example.shubham.themoviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shubham on 7/15/2018.
 */

public class Movie {
     @SerializedName("poster_path")
    String posterPath;
    Boolean adult;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("genre_ids")
    ArrayList<Integer> genreIds;
    String id;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("original_language")
    String originalLanguage;
    String title;
    @SerializedName("backdrop_path")
    String backdropPath;
    Float popularity;
    @SerializedName("vote_count")
    Integer voteCount;
    Boolean video;
    @SerializedName("vote_average")
    Float voteAverage;
}

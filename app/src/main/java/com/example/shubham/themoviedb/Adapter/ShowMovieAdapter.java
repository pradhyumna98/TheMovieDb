package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.ViewHolder.MoviesTabViewHolder;
import com.example.shubham.themoviedb.R;

import java.util.List;

/**
 * Created by shubham on 7/15/2018.
 */

public class ShowMovieAdapter extends RecyclerView.Adapter<MoviesTabViewHolder> {
    List<Movie> moviesArrayList;
    Context context;
    LayoutInflater inflater;
    MoviesRowListener listener;

    public ShowMovieAdapter(Context context, List<Movie> moviesArrayList, MoviesRowListener listener) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.listener=listener;
    }

    @Override
    public MoviesTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.movie_row_layout,parent,false);
        return new MoviesTabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesTabViewHolder holder, int position) {
        holder.tvTitle.setText(moviesArrayList.get(position).getTitle());
        holder.tvOverview.setText(moviesArrayList.get(position).getOverview());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDownloadMoviesList(v,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }
}

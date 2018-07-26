package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.ViewHolder.TabViewHolder;
import com.example.shubham.themoviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shubham on 7/15/2018.
 */

public class ShowMovieAdapter extends RecyclerView.Adapter<TabViewHolder> {
    List<Movie> moviesArrayList;
    Context context;
    LayoutInflater inflater;
    RowListener listener;

    public ShowMovieAdapter(Context context, List<Movie> moviesArrayList, RowListener listener) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.listener=listener;
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.movie_row_layout,parent,false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TabViewHolder holder, int position) {
        holder.tvTitle.setText(moviesArrayList.get(position).getTitle());
//        holder.tvOverview.setText(moviesArrayList.get(position).getOverview());
        if(moviesArrayList.get(position).getPosterPath()!=null)
        Picasso.get().load(Constants.IMAGE_URL+moviesArrayList.get(position).getPosterPath()).into(holder.poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemClicked(v,holder.getAdapterPosition());
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(holder.getAdapterPosition(),holder.button.isChecked());
            }
        });
    }
    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }
}

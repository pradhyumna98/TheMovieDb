package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.MovieDAO_Impl;
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
    Database database;
    MovieDAO movieDAO;


    public ShowMovieAdapter(Context context, List<Movie> moviesArrayList, RowListener listener) {
        this.moviesArrayList = moviesArrayList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.listener=listener;
        database=Database.getInstance(context);
        movieDAO=database.getMovieDAO();
    }

    @Override
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.movie_row_layout,parent,false);
        return new TabViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TabViewHolder holder, final int position) {
        holder.tvTitle.setText(moviesArrayList.get(position).getTitle());
//        holder.tvOverview.setText(moviesArrayList.get(position).getOverview());
        if(moviesArrayList.get(position).getPosterPath()!=null)
        Picasso.get().load(Constants.IMAGE_URL+moviesArrayList.get(position).getPosterPath()).into(holder.poster);
        int[] favouriteIds;
        Boolean flag=false;
        favouriteIds=movieDAO.getFavouriteMovies();
         for (int i = 0; i < favouriteIds.length; i++) {
            int favouriteId = favouriteIds[i];
            if(favouriteId==moviesArrayList.get(position).getId())
            flag=true;
        }
        if(flag)
        {
            holder.button.setChecked(true);
        }
        else
        {
            holder.button.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemClicked(v,holder.getAdapterPosition());
            }
        });
        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ScaleAnimation scaleAnimation;
                BounceInterpolator bounceInterpolator;
                scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
                scaleAnimation.setDuration(500);
                bounceInterpolator = new BounceInterpolator();
                scaleAnimation.setInterpolator(bounceInterpolator);
                buttonView.startAnimation(scaleAnimation);
                listener.onButtonClicked(holder.getAdapterPosition(),isChecked,moviesArrayList.get(holder.getAdapterPosition()));

            }
        });
    }
    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }
}

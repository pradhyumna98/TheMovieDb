package com.example.shubham.themoviedb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shubham on 7/15/2018.
 */

public class MoviesTabViewHolder extends RecyclerView.ViewHolder {
    View itemView;
    TextView tvTitle,tvOverview;
    public MoviesTabViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        tvTitle=itemView.findViewById(R.id.tvTitle);
        tvOverview=itemView.findViewById(R.id.tvOverview);
    }
}

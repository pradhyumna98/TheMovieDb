package com.example.shubham.themoviedb.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.themoviedb.R;

/**
 * Created by shubham on 7/15/2018.
 */

public class TabViewHolder extends RecyclerView.ViewHolder {
   public View itemView;
    public TextView tvTitle,tvOverview;
    public ImageView poster;
    public TabViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        tvTitle=itemView.findViewById(R.id.tvTitle);
        poster=itemView.findViewById(R.id.poster);
    }
}

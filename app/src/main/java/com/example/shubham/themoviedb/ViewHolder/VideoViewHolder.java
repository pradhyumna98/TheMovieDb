package com.example.shubham.themoviedb.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shubham.themoviedb.R;

/**
 * Created by shubham on 7/30/2018.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public TextView tvTitle,tvType,tvSite;
    public VideoViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        tvTitle=view.findViewById(R.id.tvTitle);
        tvSite=view.findViewById(R.id.tvSite);
        tvType=view.findViewById(R.id.tvType);
    }

}

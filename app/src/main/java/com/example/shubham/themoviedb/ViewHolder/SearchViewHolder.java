package com.example.shubham.themoviedb.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.shubham.themoviedb.R;

/**
 * Created by shubham on 7/27/2018.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public TextView tvTitle,tvOverView,tvDate,tvType;
    public ImageView poster;
    public SearchViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        tvType=itemView.findViewById(R.id.textView8);
        tvTitle=itemView.findViewById(R.id.textView6);
        tvOverView=itemView.findViewById(R.id.textView5);
        tvDate=itemView.findViewById(R.id.textView7);
        poster=itemView.findViewById(R.id.posterSearch);
    }
}

package com.example.shubham.themoviedb.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.shubham.themoviedb.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shubham on 7/26/2018.
 */

public class CastViewHolder extends RecyclerView.ViewHolder {
    public View itemView;
    public TextView tvName,tvCharacterName;
    public CircleImageView profile;
    public CastViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        tvName=itemView.findViewById(R.id.textView2);
        profile=itemView.findViewById(R.id.profile_image);
        tvCharacterName=itemView.findViewById(R.id.textView4);
    }
}

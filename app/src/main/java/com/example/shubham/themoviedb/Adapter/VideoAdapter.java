package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.ViewHolder.VideoViewHolder;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.Video;

import java.util.List;

/**
 * Created by shubham on 7/30/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    Context context;
    List<Video> videos;
    LayoutInflater inflater;
    VideoListener listener;

    public VideoAdapter(Context context, List<Video> videos, VideoListener listener) {
        this.context = context;
        this.videos = videos;
        this.listener = listener;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.video_row,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        Video video=videos.get(holder.getAdapterPosition());
        holder.tvTitle.setText(video.getName());
        holder.tvSite.setText(video.getSite());
        holder.tvType.setText(video.getType());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemClicked(holder.getAdapterPosition(),v,videos.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}

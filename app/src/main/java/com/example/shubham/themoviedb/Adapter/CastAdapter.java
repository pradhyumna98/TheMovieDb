package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.ViewHolder.CastViewHolder;
import com.example.shubham.themoviedb.ViewHolder.TabViewHolder;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shubham on 7/26/2018.
 */

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {
    List<People> peoplesList;
    Context context;
    LayoutInflater inflater;
    CastRowListener listener;
    public CastAdapter(Context context, List<People> peoplesList, CastRowListener listener) {
        this.peoplesList = peoplesList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.listener=listener;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cast_row_layout,parent,false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CastViewHolder holder, int position) {
        holder.tvName.setText(peoplesList.get(position).getName());
        holder.tvCharacterName.setText(peoplesList.get(position).getCharacter());
        if(peoplesList.get(position).getProfilePath()!=null)
            Picasso.get().load(Constants.IMAGE_URL+peoplesList.get(position).getProfilePath()).into(holder.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCastListItemClicked(v,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return peoplesList.size();
    }
}

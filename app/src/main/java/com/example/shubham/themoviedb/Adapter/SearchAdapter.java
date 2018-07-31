package com.example.shubham.themoviedb.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.ViewHolder.CastViewHolder;
import com.example.shubham.themoviedb.ViewHolder.SearchViewHolder;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 7/27/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
    Context context;
    List<SearchItems> items;
    LayoutInflater inflater;
    SearchRowListener listener;

    public SearchAdapter(Context context, List<SearchItems> items,SearchRowListener listener) {
        this.context = context;
        this.items = items;
        this.listener=listener;
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.search_row,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        switch (items.get(position).getTYPE())
        {
            case 0: final Movie movie= (Movie) items.get(position);
                    holder.tvTitle.setText(movie.getTitle());
                    holder.tvType.setText("Movie");
                    holder.tvOverView.setText(movie.getOverview());
                    Picasso.get().load(Constants.IMAGE_URL+movie.getPosterPath()).fit().into(holder.poster);
                    if (!movie.getReleaseDate().equals(""))
                    {
                        String[] date=movie.getReleaseDate().split("-");
                        holder.tvDate.setText(date[0]);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onMovieItemClicked(holder.getAdapterPosition(),movie);
                        }
                    });
                    break;
            case 1:
                final Shows show= (Shows) items.get(position);
                   holder.tvTitle.setText(show.getName());
                   holder.tvOverView.setText(show.getOverview());
                    holder.tvType.setText("TV Show");
                   Picasso.get().load(Constants.IMAGE_URL+show.getPosterPath()).fit().into(holder.poster);
                   if (!show.getFirstAirDate().equals(""))
                    {
                      String[] date=show.getFirstAirDate().split("-");
                      holder.tvDate.setText(date[0]);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onShowItemClicked(holder.getAdapterPosition(),show);
                        }
                    });
                    break;
            case 2: final People people= (People) items.get(position);
                    holder.tvTitle.setText(people.getName());
                    holder.tvType.setText("People");
                    holder.tvOverView.setText("");
                    holder.tvDate.setText("");
                    Picasso.get().load(Constants.IMAGE_URL+people.getProfilePath()).fit().into(holder.poster);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onPeopleItemClicked(holder.getAdapterPosition(),people);
                        }
                    });
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

package com.example.shubham.themoviedb.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shubham.themoviedb.Activities.Details;
import com.example.shubham.themoviedb.Adapter.RowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Adapter.ShowsTvAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.Activities.GridActivity;
import com.example.shubham.themoviedb.ListLoadListener;
import com.example.shubham.themoviedb.LoadList;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.FavouriteMovies;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.Movies.NowShowingMovie;
import com.example.shubham.themoviedb.entities.Movies.PopularMovie;
import com.example.shubham.themoviedb.entities.Movies.TopRatedMovie;
import com.example.shubham.themoviedb.entities.Movies.UpcomingMovie;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.TvShows.AirTodayShows;
import com.example.shubham.themoviedb.entities.TvShows.FavouriteShows;
import com.example.shubham.themoviedb.entities.TvShows.OnAirShows;
import com.example.shubham.themoviedb.entities.TvShows.PopularShows;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.example.shubham.themoviedb.entities.TvShows.TopRatedShows;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListLoadListener {
RecyclerView RV;
ProgressBar progressBar;
TextView tv;
Button btnViewAll;
Database database;
List<Movie> movies=new ArrayList<>();
ShowMovieAdapter moviesAdapter;
List<Shows> shows=new ArrayList<>();
ShowsTvAdapter showsAdapter;
String type,fragment;
Bundle bundle;
LoadList listLoader;
int page=1;
long id;
MovieDAO movieDAO;
ShowDAO showDAO;
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output=inflater.inflate(R.layout.fragment_list, container, false);
        RV=output.findViewById(R.id.RecyclerView);
        progressBar=output.findViewById(R.id.progressBar);
        tv=output.findViewById(R.id.textView);
        btnViewAll=output.findViewById(R.id.button);
        database=Database.getInstance(getContext());
        movieDAO=database.getMovieDAO();
        showDAO=database.getShowDAO();
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        bundle=getArguments();
        listLoader=new LoadList(bundle,getContext(),this);
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), GridActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        fragment=bundle.getString(Constants.FRAGMENT);
        type=bundle.getString(Constants.TYPE);
        id=bundle.getLong(Constants.ID,0);
        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            movies.addAll(listLoader.getMovies(page,id));
            if(type.equals(Constants.NOW_SHOWING_MOVIES))
            {
                tv.setText("Now Showing");
            }
            else if(type.equals(Constants.POPULAR_MOVIES))
            {
                tv.setText("Popular");
            }
            else if(type.equals(Constants.TOP_RATED_MOVIES))
            {
               tv.setText("Top Rated");
            }
            else if(type.equals(Constants.UPCOMING_MOVIES))
            {
                tv.setText("Upcoming");
            }
            else if(type.equals(Constants.SIMILAR_MOVIES))
            {
                tv.setText("Similar");
            }
            if(movies.size()>0)
            {
                RV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            moviesAdapter=new ShowMovieAdapter(getContext(), movies, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {
                    Intent intent=new Intent(getActivity(), Details.class);
                    intent.putExtra(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
                    long id=movies.get(position).getId();
                    intent.putExtra(Constants.ID,id);
                    getActivity().startActivity(intent);
                }

                @Override
                public void onButtonClicked(int position, Boolean checked, SearchItems items) {
                   Movie movie= (Movie) items;
                    FavouriteMovies favouriteMovies=new FavouriteMovies();
                    favouriteMovies.setMovieId(movie.getId());
                    if(checked)
                    {
                        movieDAO.insertMovie(movie);
                        movieDAO.insertFavouriteMovie(favouriteMovies);
                    }
                    else {
                        movieDAO.deleteMovies(movie);
                    }
                }
            });
            RV.setAdapter(moviesAdapter);
        }
        else if(fragment.equals(Constants.SHOWS_FRAGMENT))
        {
            shows.addAll(listLoader.getShows(page,id));
            if(type.equals(Constants.AIR_TODAY_SHOWS))
            {
               tv.setText("Airing Today");
            }
            else if(type.equals(Constants.POPULAR_SHOWS))
            {
                tv.setText("Popular");
            }
            else if(type.equals(Constants.TOP_RATED_SHOWS))
            {
               tv.setText("Top Rated");
            }
            else if(type.equals(Constants.ON_AIR_SHOWS))
            {
               tv.setText("On Air");
            }
            else if(type.equals(Constants.SIMILAR_SHOWS))
            {
                tv.setText("Similar");
            }
            if(shows.size()>0)
            {
                RV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            showsAdapter=new ShowsTvAdapter(getContext(), shows, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {
                    Intent intent=new Intent(getActivity(), Details.class);
                    intent.putExtra(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
                    intent.putExtra(Constants.ID,shows.get(position).getId());
                    getActivity().startActivity(intent);
                }

                @Override
                public void onButtonClicked(int position,Boolean checked,SearchItems items) {
                    Shows shows= (Shows) items;
                    FavouriteShows favouriteShows=new FavouriteShows();
                    favouriteShows.setShowId(shows.getId());
                    if(checked)
                    {
                        showDAO.insertShow(shows);
                        showDAO.insertFavouriteShow(favouriteShows);

                    }
                    else
                    {
                        showDAO.deleteShows(shows);
                    }
                }
            });
            RV.setAdapter(showsAdapter);
        }
        
        return output;
    }

    @Override
    public void onMoviesListLoaded(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();
        if(movies.size()>0)
        {
            RV.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onShowsListLoaded(List<Shows> shows) {
        this.shows.clear();
        this.shows.addAll(shows);
        showsAdapter.notifyDataSetChanged();
        if(shows.size()>0)
        {
            RV.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

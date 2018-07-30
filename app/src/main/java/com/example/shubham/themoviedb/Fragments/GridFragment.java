package com.example.shubham.themoviedb.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.themoviedb.Activities.Details;
import com.example.shubham.themoviedb.Activities.GridActivity;
import com.example.shubham.themoviedb.Adapter.RowListener;
import com.example.shubham.themoviedb.Adapter.ShowMovieAdapter;
import com.example.shubham.themoviedb.Adapter.ShowsTvAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.EndlessRecyclerViewScrollListener;
import com.example.shubham.themoviedb.ListLoadListener;
import com.example.shubham.themoviedb.LoadList;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.FavouriteMovies;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.TvShows.FavouriteShows;
import com.example.shubham.themoviedb.entities.TvShows.Shows;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment implements ListLoadListener {
    RecyclerView RV;
    LoadList listLoader;
    String fragment,type;
    ShowMovieAdapter moviesAdapter;
    ShowsTvAdapter showsAdapter;
    List<Movie> movies=new ArrayList<>();
    List<Shows> shows=new ArrayList<>();
    int page=1;
    long id;
    Bundle bundle=new Bundle();
    Database database;
    MovieDAO movieDAO;
    ShowDAO showDAO;
    EndlessRecyclerViewScrollListener listener;
    GridLayoutManager manager;

    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output=inflater.inflate(R.layout.fragment_grid, container, false);
        RV=output.findViewById(R.id.recyclerGrid);
        manager=new GridLayoutManager(getContext(),3);
        RV.setLayoutManager(manager);
        database=Database.getInstance(getContext());
        movieDAO=database.getMovieDAO();
        showDAO=database.getShowDAO();
        bundle=getArguments();
        listLoader=new LoadList(bundle,getContext(),this);
        fragment=bundle.getString(Constants.FRAGMENT);
        type=bundle.getString(Constants.TYPE);
        id=bundle.getLong(Constants.ID,0);
        listener=new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                LoadList loadList=new LoadList(bundle, getContext(), new ListLoadListener() {
                    @Override
                    public void onMoviesListLoaded(List<Movie> movies) {
                        GridFragment.this.movies.addAll(movies);

                        moviesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onShowsListLoaded(List<Shows> shows) {
                        GridFragment.this.shows.addAll(shows);

                        showsAdapter.notifyDataSetChanged();
                    }
                });
                if(fragment.equals(Constants.MOVIES_FRAGMENT))
                    loadList.getMovies(page,id);
                else if(fragment.equals(Constants.SHOWS_FRAGMENT))
                    loadList.getShows(page,id);
            }
        };
        RV.addOnScrollListener(listener);
        if(fragment.equals(Constants.MOVIES_FRAGMENT))
        {
            movies.addAll(listLoader.getMovies(page,id));
            moviesAdapter=new ShowMovieAdapter(getContext(),movies, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {
                    Intent intent=new Intent(getActivity(), Details.class);
                    intent.putExtra(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
                    intent.putExtra(Constants.ID,(long)movies.get(position).getId());
                    startActivity(intent);
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
            showsAdapter=new ShowsTvAdapter(getContext(),shows, new RowListener() {
                @Override
                public void onListItemClicked(View view, int position) {
                    Intent intent=new Intent(getActivity(), Details.class);
                    intent.putExtra(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
                    intent.putExtra(Constants.ID,shows.get(position).getId());
                    startActivity(intent);
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
        else if(fragment.equals(Constants.FAVOURITE_FRAGMENT))
        {
            if(type.equals(Constants.FAVOURIT_MOVIES))
            {
                movies.addAll(listLoader.getFavouriteMovies());
                moviesAdapter=new ShowMovieAdapter(getContext(),movies, new RowListener() {
                    @Override
                    public void onListItemClicked(View view, int position) {
                        Intent intent=new Intent(getActivity(), Details.class);
                        intent.putExtra(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
                        intent.putExtra(Constants.ID,(long)movies.get(position).getId());
                        startActivity(intent);
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
            else if(type.equals(Constants.FAVOURITE_SHOWS))
            {
                shows.addAll(listLoader.getFavouriteShows());
                showsAdapter=new ShowsTvAdapter(getContext(),shows, new RowListener() {
                    @Override
                    public void onListItemClicked(View view, int position) {
                        Intent intent=new Intent(getActivity(), Details.class);
                        intent.putExtra(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
                        intent.putExtra(Constants.ID,shows.get(position).getId());
                        startActivity(intent);
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
        }
        return output;
    }

    @Override
    public void onMoviesListLoaded(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowsListLoaded(List<Shows> shows) {
        this.shows.clear();
        this.shows.addAll(shows);
        showsAdapter.notifyDataSetChanged();
    }
}

package com.example.shubham.themoviedb.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shubham.themoviedb.Adapter.SearchAdapter;
import com.example.shubham.themoviedb.Adapter.SearchRowListener;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.EndlessRecyclerViewScrollListener;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;
import com.example.shubham.themoviedb.entities.Movies.Movie;
import com.example.shubham.themoviedb.entities.People;
import com.example.shubham.themoviedb.entities.SearchItems;
import com.example.shubham.themoviedb.entities.TvShows.Shows;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResult extends AppCompatActivity {
RecyclerView RV;
ProgressBar progressBar;
String search;
SearchAdapter adapter;
List<SearchItems> items=new ArrayList<>();
LinearLayoutManager manager;
EndlessRecyclerViewScrollListener listener;
int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        RV=findViewById(R.id.RecyclerViewSearch);
        final Intent intent=getIntent();
        search=intent.getStringExtra(Constants.QUERY);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        listener=new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Call<ResponseBody> call= ApiClient.getMovieDBService().getSearchResult(Constants.API_KEY,search,page);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body()!=null)
                        {
                            try {
                                JSONObject root=new JSONObject(response.body().string());
                                JSONArray array=root.getJSONArray("results");
                                Gson gson=new Gson();
                                for(int i=0;i<array.length();i++)
                                {
                                    JSONObject object=array.getJSONObject(i);
                                    String mediaType=object.getString("media_type");
                                    if(mediaType.equals("movie")){
                                        Movie movie=gson.fromJson(object.toString(),Movie.class);
                                        items.add(movie);
                                    }
                                    else if(mediaType.equals("tv"))
                                    {
                                        Shows show=gson.fromJson(object.toString(),Shows.class);
                                        items.add(show);
                                    }
                                    else if(mediaType.equals("person"))
                                    {
                                        People people=gson.fromJson(object.toString(),People.class);
                                        items.add(people);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        };
        adapter=new SearchAdapter(this, items, new SearchRowListener() {
            @Override
            public void onMovieItemClicked(int position,Movie movie) {
                Intent intent1=new Intent(SearchResult.this,Details.class);
                intent1.putExtra(Constants.FRAGMENT,Constants.MOVIES_FRAGMENT);
                intent1.putExtra(Constants.ID,(long)movie.getId());
                startActivity(intent1);
            }

            @Override
            public void onShowItemClicked(int position,Shows show) {
                Intent intent1=new Intent(SearchResult.this,Details.class);
                intent1.putExtra(Constants.FRAGMENT,Constants.SHOWS_FRAGMENT);
                intent1.putExtra(Constants.ID,(long)show.getId());
                startActivity(intent1);
            }

            @Override
            public void onPeopleItemClicked(int position,People people) {
                Intent intent1=new Intent(SearchResult.this,CastDetailsActivity.class);
                intent1.putExtra(Constants.ID,(long)people.getId());
                startActivity(intent1);
            }
        });
        RV.addOnScrollListener(listener);
        RV.setLayoutManager(manager);
        RV.setAdapter(adapter);
        progressBar=findViewById(R.id.progressBarSearch);
        Call<ResponseBody> call= ApiClient.getMovieDBService().getSearchResult(Constants.API_KEY,search,page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body()!=null)
                {
                    try {
                        JSONObject root=new JSONObject(response.body().string());
                        JSONArray array=root.getJSONArray("results");
                        Gson gson=new Gson();
                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject object=array.getJSONObject(i);
                            String mediaType=object.getString("media_type");
                            if(mediaType.equals("movie")){
                                Movie movie=gson.fromJson(object.toString(),Movie.class);
                                items.add(movie);
                            }
                            else if(mediaType.equals("tv"))
                            {
                                Shows show=gson.fromJson(object.toString(),Shows.class);
                                items.add(show);
                            }
                            else if(mediaType.equals("person"))
                            {
                                People people=gson.fromJson(object.toString(),People.class);
                                items.add(people);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        RV.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(SearchResult.this,"No Result Found",Toast.LENGTH_LONG).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },2000);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

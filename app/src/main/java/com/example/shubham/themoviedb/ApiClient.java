package com.example.shubham.themoviedb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shubham on 7/15/2018.
 */

public class ApiClient {

        private static Retrofit retrofit;

        private static MovieDBService service;

        static Retrofit getInstance(){
            if (retrofit == null) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create());
                retrofit = builder.build();
            }
            return retrofit;
        }

        static MovieDBService getMovieDBService(){
            if(service == null){
                service = getInstance().create(MovieDBService.class);
            }
            return service;
        }
    }


package com.example.rajus.newsviews.Api;

import com.example.rajus.newsviews.ModelClass.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rajus on 2/16/2019.
 */

public interface ApiInterfaceNews {

    @GET("top-headlines")
    Call<News> getNews(
            @Query("sources") String source,
            @Query("apiKey") String apiKey

    );

}

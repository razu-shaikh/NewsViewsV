package com.example.rajus.newsviews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rajus.newsviews.Api.ApiClient;
import com.example.rajus.newsviews.Api.ApiInterface;
import com.example.rajus.newsviews.Api.ApiInterfaceNews;
import com.example.rajus.newsviews.ModelClass.Article;
import com.example.rajus.newsviews.ModelClass.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "c28282ab9fa44f5a9e211b0e8b91bca1";
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;
    private List<Article> articles = new ArrayList<>();
    private List<Article> article = new ArrayList<>();
    private Adapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        //loadJson();
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        layoutManager2 = new LinearLayoutManager(MainActivity.this);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setNestedScrollingEnabled(false);
        //loadJsonJson();
        onLoadingSwipeRefresh("","");

    }

    public void loadJson(final String keyword){
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();

        Call<News> call;
       call = apiInterface.getNews(country,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this,"NO Result", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }
    public void loadJsonJson(final String keyword2){
        ApiInterfaceNews apiInterface = ApiClient.getApiClient().create(ApiInterfaceNews.class);
        //String country = Utils.getCountry();

        Call<News> call;
        //call = apiInterface.getNews(country,API_KEY);
        call = apiInterface.getNews("techcrunch",API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){

                    if (!article.isEmpty()){
                        articles.clear();
                    }

                    article = response.body().getArticle();
                    adapter = new Adapter(article, MainActivity.this);
                    recyclerView2.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(MainActivity.this,"NO Result", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }

    @Override
    public void onRefresh() {
        loadJson("");
        loadJsonJson("");
    }

    private void onLoadingSwipeRefresh(final String keyword, final String keyword2){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        loadJson(keyword);
                        loadJsonJson(keyword2);
                    }
                }
        );

    }
}

package com.sjh.news.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Adapter.NewsAdapter;
import com.sjh.news.Adapter.NewsListAdapter;
import com.sjh.news.Domain.News;
import com.sjh.news.Domain.NewsJson;
import com.sjh.news.Domain.NewsListJson;
import com.sjh.news.Executor.CachedExecutorService;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.HttpInfo;
import com.sjh.news.StaicInfo.NewsInfo;
import com.sjh.news.StaicInfo.TagInfo;
import com.sjh.news.StaicInfo.UserInfo;
import com.sjh.news.Util.HttpUtil;
import com.sjh.news.Util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txt_username;
    private TextView txt_email;
    //设置新闻ListView & Adapter
    private SwipeRefreshLayout refreshLayout;
    private ListView list_news;
    private LayoutInflater groupPollingAddress;
    public NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //设置ListView & Adapter
        list_news = findViewById(R.id.list_news);
        refreshLayout = findViewById(R.id.swipe_layout);
        newsAdapter = new NewsAdapter(this, R.layout.adapter_news, NewsInfo.newsArrayList);
        list_news.setAdapter(newsAdapter);

        //设置侧边栏用户名及邮箱
        groupPollingAddress = (LayoutInflater) NewsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) groupPollingAddress.inflate(R.layout.nav_header_login, null);
        txt_username = linearLayout.findViewById(R.id.txt_username);
        txt_email = linearLayout.findViewById(R.id.txt_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (!"username".equals(UserInfo.username) && txt_username != null) {
            System.out.println("Set UserName");
            txt_username.setText("465");
        }
        if (!"email".equals(UserInfo.email) && txt_email != null) {
            System.out.println("Set email");
            txt_email.setText(UserInfo.email);
        }

        //ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.recommend);

        //设置listView点击事件
        list_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = new Intent(NewsActivity.this, ContentActivity.class);
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News news = NewsInfo.newsArrayList.get(position);
                intent.putExtra("title", news.getNewsTitle());
                intent.putExtra("uri", news.getUrl());
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //下拉刷新事件
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                String tag = actionBar.getTitle().toString();
                String url;
                switch (tag) {
                    case TagInfo.RECOMMEND:
                        url = HttpInfo.URL_RECOMMEND;
                        break;
                    case TagInfo.TECHNOLOGY:
                        url = HttpInfo.URL_TECHNOLOGY;
                        break;
                    case TagInfo.FUN:
                        url = HttpInfo.URL_FUN;
                        break;
                    case TagInfo.MILITARY:
                        url = HttpInfo.URL_MILITARY;
                        break;
                    case TagInfo.IT:
                        url = HttpInfo.URL_IT;
                        break;
                    case TagInfo.FOOTBALL:
                        url = HttpInfo.URL_FOOTBALL;
                        break;
                    case TagInfo.NBA:
                        url = HttpInfo.URL_NBA;
                        break;
                    default:
                        url = HttpInfo.URL_RECOMMEND;
                }
                requestNew(url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void closeDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.recommend);


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recommend) {

            isCurrentPage(HttpInfo.URL_RECOMMEND);
            actionBar.setTitle(R.string.recommend);
            TagInfo.currentTag = TagInfo.RECOMMEND;
            closeDrawer();

        } else if (id == R.id.nav_technology) {

            isCurrentPage(HttpInfo.URL_TECHNOLOGY);
            actionBar.setTitle(R.string.technology);
            TagInfo.currentTag = TagInfo.TECHNOLOGY;
            closeDrawer();

        } else if (id == R.id.nav_fun) {

            isCurrentPage(HttpInfo.URL_FUN);
            actionBar.setTitle(R.string.fun);
            TagInfo.currentTag = TagInfo.FUN;
            closeDrawer();

        } else if (id == R.id.nav_military) {

            isCurrentPage(HttpInfo.URL_MILITARY);
            actionBar.setTitle(R.string.military);
            TagInfo.currentTag = TagInfo.MILITARY;
            closeDrawer();

        } else if (id == R.id.nav_it) {

            isCurrentPage(HttpInfo.URL_IT);
            actionBar.setTitle(R.string.it);
            TagInfo.currentTag = TagInfo.IT;
            closeDrawer();

        } else if (id == R.id.nav_football) {

            isCurrentPage(HttpInfo.URL_FOOTBALL);
            actionBar.setTitle(R.string.football);
            TagInfo.currentTag = TagInfo.FOOTBALL;
            closeDrawer();

        } else if (id == R.id.nav_nba) {

            isCurrentPage(HttpInfo.URL_NBA);
            actionBar.setTitle(R.string.nba);
            TagInfo.currentTag = TagInfo.NBA;
            closeDrawer();

        } else if (id == R.id.nav_share) {

//            closeDrawer();
//            refreshLayout.setRefreshing(true);

        } else if (id == R.id.nav_send) {

//            closeDrawer();
//            refreshLayout.setRefreshing(true);

        }
        return true;
    }


    private void isCurrentPage(String url) {
            String currentUrl;
            switch (TagInfo.currentTag) {
                case TagInfo.RECOMMEND:
                    currentUrl = HttpInfo.URL_RECOMMEND;
                    break;
                case TagInfo.TECHNOLOGY:
                    currentUrl = HttpInfo.URL_TECHNOLOGY;
                    break;
                case TagInfo.FUN:
                    currentUrl = HttpInfo.URL_FUN;
                    break;
                case TagInfo.MILITARY:
                    currentUrl = HttpInfo.URL_MILITARY;
                    break;
                case TagInfo.IT:
                    currentUrl = HttpInfo.URL_IT;
                    break;
                case TagInfo.FOOTBALL:
                    currentUrl = HttpInfo.URL_FOOTBALL;
                    break;
                case TagInfo.NBA:
                    currentUrl = HttpInfo.URL_NBA;
                    break;
                default:
                    currentUrl = HttpInfo.URL_RECOMMEND;
            }
            if (!url.equals(currentUrl)) {
                requestNew(url);
                refreshLayout.setRefreshing(true);
            }
    }

    public void requestNew(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NewsActivity.this, R.string.getNewsError, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseTest = response.body().string();
                final NewsListJson newsListJson = JsonUtil.jsonToNewsList(responseTest);
                final int code = newsListJson.getCode();
                final String msg = newsListJson.getMsg();
                if (code == 200) {
                    //清除现有的新闻
                    NewsInfo.newsArrayList.clear();
                    for (NewsJson newsJson : newsListJson.getNewsList()) {
                        News news = new News(newsJson.getUrl(), newsJson.getTitle(), newsJson.getDescription(), newsJson.getPicUrl());
                        NewsInfo.newsArrayList.add(news);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsAdapter.notifyDataSetChanged();
                            list_news.setSelection(0);
                            refreshLayout.setRefreshing(false);
                        };
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewsActivity.this, R.string.getNewsError,Toast.LENGTH_SHORT).show();
                            refreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }
}

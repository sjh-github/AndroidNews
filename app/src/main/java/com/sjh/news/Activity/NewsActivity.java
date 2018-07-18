package com.sjh.news.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjh.news.Adapter.NewsAdapter;
import com.sjh.news.Domain.Interests;
import com.sjh.news.Domain.News;
import com.sjh.news.Domain.User;
import com.sjh.news.Domain.fx.DataJson;
import com.sjh.news.Domain.fx.RootJson;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.HttpInfo;
import com.sjh.news.StaicInfo.NewsInfo;
import com.sjh.news.StaicInfo.TagInfo;
import com.sjh.news.StaicInfo.UserInfo;
import com.sjh.news.Util.HttpUtil;
import com.sjh.news.Util.JsonUtil;
import com.sjh.news.Util.MD5Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView img_userImage;
    private TextView txt_username;
    private TextView txt_email;
    //设置新闻ListView & Adapter
    private SwipeRefreshLayout refreshLayout;
    private ListView list_news;
    private LayoutInflater groupPollingAddress;
    public NewsAdapter newsAdapter;

    private ActionBar actionBar;
    //private SQLiteDatabase db;


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
        img_userImage = linearLayout.findViewById(R.id.imageView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //db = openOrCreateDatabase("user.db", Context.MODE_PRIVATE, null);

        interestReCal("推荐");

        if (!"username".equals(UserInfo.username) && txt_username != null) {
            System.out.println("-------------------Set UserName");
            txt_username.setText("465");
        }
        if (!"email".equals(UserInfo.email) && txt_email != null) {
            System.out.println("Set email");
            txt_email.setText(UserInfo.email);
        }

        //ActionBar
        actionBar = getSupportActionBar();
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

                final String interestTag = actionBar.getTitle().toString();
                interestReCal(interestTag);

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

                String timestamp = String.valueOf(System.currentTimeMillis());
                String signature;
                try {
                    signature = MD5Util.getMD5Str(HttpInfo.SecretKeyValue + timestamp + HttpInfo.AccessKeyValue);

                } catch (Exception e) {
                    Toast.makeText(NewsActivity.this, "数据加密错误", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return;
                }

                refreshLayout.setRefreshing(true);
                String tag = actionBar.getTitle().toString();
                String url;
                switch (tag) {
                    case TagInfo.RECOMMEND:
                        url = HttpInfo.URL_RECOMMEND;
                        requestNewRecommend();
                        break;
                    case TagInfo.TECHNOLOGY:
                        url = "https://api.xinwen.cn/news/hot?category=Tech&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    case TagInfo.FUN:
                        url = "https://api.xinwen.cn/news/hot?category=Entertainment&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    case TagInfo.MILITARY:
                        url = "https://api.xinwen.cn/news/hot?category=Military&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    case TagInfo.IT:
                        url = "https://api.xinwen.cn/news/hot?category=Society&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    case TagInfo.FOOTBALL:
                        url = "https://api.xinwen.cn/news/hot?category=Society&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    case TagInfo.NBA:
                        url = "https://api.xinwen.cn/news/hot?category=World&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                        break;
                    default:
                        url = "https://api.xinwen.cn/news/search?q=" + TagInfo.SEARCH + "&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                        requestNew(url, false, true);
                }
            }
        });
        requestNewRecommend();
    }

    public void updateCustomTag(View view) {
        Toast.makeText(NewsActivity.this, "点击头像", Toast.LENGTH_LONG).show();
    }

    private void interestReCal(String interestTag) {
        int total = 0;
        User currentUser = null;
        /*Cursor userCursor = db.query("userdb", null, null, null, null, null, null);
        if (userCursor.moveToFirst()) {
            for (int i = 0; i < userCursor.getCount(); i++) {
                userCursor.move(i);
                String dbusername = userCursor.getString(userCursor.getColumnIndex("username"));
                String dbpassword = userCursor.getString(userCursor.getColumnIndex("password"));
                String dbemail = userCursor.getString(userCursor.getColumnIndex("email"));
                Integer dbtechnologyNum = userCursor.getInt(userCursor.getColumnIndex("technology"));
                Integer dbfunNum = userCursor.getInt(userCursor.getColumnIndex("fun"));
                Integer dbmilitaryNum = userCursor.getInt(userCursor.getColumnIndex("military"));
                Integer dbitNum = userCursor.getInt(userCursor.getColumnIndex("it"));
                Integer dbfootballNum = userCursor.getInt(userCursor.getColumnIndex("football"));
                Integer dbnbaNum = userCursor.getInt(userCursor.getColumnIndex("nba"));
                User user = new User(dbusername, dbpassword, dbemail);
                Map<String, Integer> map = new HashMap<>();
                map.put(Interests.TECHNOLOGY, dbtechnologyNum);
                map.put(Interests.FUN, dbfunNum);
                map.put(Interests.MILITARY, dbmilitaryNum);
                map.put(Interests.IT, dbitNum);
                map.put(Interests.FOOTBALL, dbfootballNum);
                map.put(Interests.NBA, dbnbaNum);
                user.setUserInterest(map);
                System.out.println(map.keySet());
                System.out.println(map.values());
                System.out.println("=-*: " + user.toString());
                UserInfo.userArrayList.add(user);
                System.out.println("=-*: " + UserInfo.userArrayList.get(0).toString());
            }
        }
        userCursor.close();
        System.out.println(UserInfo.userArrayList.get(0).toString());*/
        for (User user : UserInfo.userArrayList) {
            if (user.getUsername().equals(UserInfo.username)) {
                currentUser = user;
                break;
            }
        }
        switch (interestTag) {
            case TagInfo.TECHNOLOGY:
                currentUser.getUserInterest().put(Interests.TECHNOLOGY, currentUser.getUserInterest().get(Interests.TECHNOLOGY) + 1);
                break;
            case TagInfo.FUN:
                currentUser.getUserInterest().put(Interests.FUN, currentUser.getUserInterest().get(Interests.FUN) + 1);
                break;
            case TagInfo.MILITARY:
                currentUser.getUserInterest().put(Interests.MILITARY, currentUser.getUserInterest().get(Interests.MILITARY) + 1);
                break;
            case TagInfo.FOOTBALL:
                currentUser.getUserInterest().put(Interests.FOOTBALL, currentUser.getUserInterest().get(Interests.FOOTBALL) + 1);
                break;
            case TagInfo.IT:
                currentUser.getUserInterest().put(Interests.IT, currentUser.getUserInterest().get(Interests.IT) + 1);
                break;
            case TagInfo.NBA:
                currentUser.getUserInterest().put(Interests.NBA, currentUser.getUserInterest().get(Interests.NBA) + 1);
                break;
        }

        //写入数据库
        /*String sql = "update userdb set technology = " + currentUser.getUserInterest().get(Interests.TECHNOLOGY) + ", "
                                        + "fun = " + currentUser.getUserInterest().get(Interests.FUN) + ", "
                                        + "military = " + currentUser.getUserInterest().get(Interests.MILITARY) + ", "
                                        + "it = " + currentUser.getUserInterest().get(Interests.IT) + ", "
                                        + "football = " + currentUser.getUserInterest().get(Interests.FOOTBALL) + ", "
                                        + "nba = " + currentUser.getUserInterest().get(Interests.NBA) + " where username = \'" + currentUser.getUsername() + "\'";
        System.out.println("-----:" + sql);
        db.execSQL(sql);*/


        for (Integer num : currentUser.getUserInterest().values()) {
            total += num;
        }
        HttpInfo.technologyNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.TECHNOLOGY)/total;
        HttpInfo.funNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.FUN)/total;
        HttpInfo.itNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.IT)/total;
        HttpInfo.militaryNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.MILITARY)/total;
        HttpInfo.footballNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.FOOTBALL)/total;
        HttpInfo.nbaNum = (HttpInfo.NUM - 1) * currentUser.getUserInterest().get(Interests.NBA)/total;
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

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.news_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                String timestamp = String.valueOf(System.currentTimeMillis());
                String signature;
                try {
                    signature = MD5Util.getMD5Str(HttpInfo.SecretKeyValue + timestamp + HttpInfo.AccessKeyValue);
                } catch (Exception e) {
                    Toast.makeText(NewsActivity.this, "数据加密错误", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    return false;
                }
                String searchURL = "https://api.xinwen.cn/news/search?q=" + query + "&size=" + HttpInfo.NUM + "&signature=" + signature + "&order=relevance&timestamp=" + timestamp +"&access_key=fFZK5gezvwnEZ8CV";
                requestNew(searchURL, false, true);
                refreshLayout.setRefreshing(true);
                actionBar.setTitle(query);
                TagInfo.SEARCH = query;
                TagInfo.currentTag = TagInfo.SEARCH;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                return true;
            }
        });


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

        } else if (id == R.id.nav_logout) {

            closeDrawer();
            Intent toLoginPageIntent = new Intent(NewsActivity.this, LoginActivity.class);
            startActivity(toLoginPageIntent);
        } else if (id == R.id.nav_custom) {

            closeDrawer();
            //自定义标签未设置
            if (TagInfo.customTag.equals("")) {
                final EditText inputServer = new EditText(this);
                inputServer.setFocusable(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请输入自定义关键字")
                        .setIcon(R.drawable.logo_without_text)
                        .setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("保存",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String inputName = inputServer.getText().toString();
                                if (inputName.length() > 0) {
                                    TagInfo.customTag = inputName;
                                    TagInfo.currentTag = TagInfo.customTag;
                                    getCustomNews();
                                    actionBar.setTitle(TagInfo.customTag);
                                    closeDrawer();
                                } else {
                                    Toast.makeText(NewsActivity.this, "关键字不能为空", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                builder.show();
            } else {
                //isCurrentPage(HttpInfo.URL_CUSTOM);
                TagInfo.currentTag = TagInfo.customTag;
                getCustomNews();
                actionBar.setTitle(TagInfo.customTag);
                closeDrawer();
            }

        } else if (id == R.id.nav_updateCustomTag) {
            closeDrawer();
            final EditText inputServer = new EditText(this);
            inputServer.setText(TagInfo.customTag);
            inputServer.setFocusable(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请输入自定义关键字")
                    .setIcon(R.drawable.logo_without_text)
                    .setView(inputServer)
                    .setNegativeButton("取消", null);
            builder.setPositiveButton("保存",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String inputName = inputServer.getText().toString();
                            if (inputName.length() > 0) {
                                TagInfo.customTag = inputName;
                                TagInfo.currentTag = TagInfo.customTag;
                                getCustomNews();
                                actionBar.setTitle(TagInfo.customTag);
                                closeDrawer();
                            } else {
                                TagInfo.customTag = "";
                                closeDrawer();
                            }
                        }
                    });
            builder.show();
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
                    currentUrl = HttpInfo.URL_SEARCH;
            }
            if (!url.equals(currentUrl)) {
                if (url.equals(HttpInfo.URL_RECOMMEND)) {
                    requestNewRecommend();
                } else {
                    requestNew(url, false, false);
                }
                refreshLayout.setRefreshing(true);
            }
    }

    public void requestNew(final String url, final boolean isRecommend, final boolean refresh) {

        String newUrl = url;
        if (!refresh && !isRecommend) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String signature;
            try {
                signature = MD5Util.getMD5Str(HttpInfo.SecretKeyValue + timestamp + HttpInfo.AccessKeyValue);

            } catch (Exception e) {
                Toast.makeText(NewsActivity.this, "数据加密错误", Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }
            switch (url) {
                case HttpInfo.URL_TECHNOLOGY:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Tech&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                case HttpInfo.URL_FUN:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Entertainment&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                case HttpInfo.URL_MILITARY:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Military&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                case HttpInfo.URL_IT:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Society&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                case HttpInfo.URL_FOOTBALL:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Sport&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                case HttpInfo.URL_NBA:
                    newUrl = "https://api.xinwen.cn/news/hot?category=World&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
                    break;
                default:
                    newUrl = "https://api.xinwen.cn/news/hot?category=Tech&size=" + HttpInfo.NUM + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
            }
        }
        HttpUtil.sendOkHttpRequest(newUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*Toast.makeText(NewsActivity.this, R.string.getNewsError, Toast.LENGTH_SHORT).show();*/
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseTest = response.body().string();
                final RootJson rootJson = JsonUtil.jsonToNewsList(responseTest);
                boolean success = rootJson.isSuccess();
                if (success) {
                    if (!isRecommend) {
                        NewsInfo.newsArrayList.clear();
                    }
                    DataJson dataJson = rootJson.getDataJson();
                    List<com.sjh.news.Domain.fx.NewsJson> newsJsonList = dataJson.getNewsJson();
                    News news = null;
                    for (com.sjh.news.Domain.fx.NewsJson newsJson : newsJsonList) {
                        if (newsJson.getImgUrl() != null && newsJson.getImgUrl().size() >= 1) {
                            news = new News(newsJson.getUrl(), newsJson.getTitle(), newsJson.getSource(), newsJson.getImgUrl().get(0));
                        } else {
                            news = new News(newsJson.getUrl(), newsJson.getTitle(), newsJson.getSource(), null);
                        }

                        NewsInfo.newsArrayList.add(news);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsAdapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                            list_news.setSelection(0);
                        }
                    });
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    //获取推荐新闻
    private void requestNewRecommend() {

        NewsInfo.newsArrayList.clear();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature;
        try {
            signature = MD5Util.getMD5Str(HttpInfo.SecretKeyValue + timestamp + HttpInfo.AccessKeyValue);
        } catch (Exception e) {
            Toast.makeText(NewsActivity.this, "数据加密错误", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        String URL_TECHNOLOGY = "https://api.xinwen.cn/news/hot?category=Tech&size=" + HttpInfo.technologyNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
        String URL_FUN = "https://api.xinwen.cn/news/hot?category=Entertainment&size=" + HttpInfo.funNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
        String URL_MILITARY = "https://api.xinwen.cn/news/hot?category=Military&size=" + HttpInfo.militaryNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
        String URL_IT = "https://api.xinwen.cn/news/hot?category=Society&size=" + HttpInfo.itNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
        String URL_FOOTBALL = "https://api.xinwen.cn/news/hot?category=Sport&size=" + HttpInfo.footballNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";
        String URL_NBA = "https://api.xinwen.cn/news/hot?category=World&size=" + HttpInfo.nbaNum + "&signature=" + signature + "&timestamp=" + timestamp + "&access_key=fFZK5gezvwnEZ8CV";


        if(HttpInfo.technologyNum != 0) {
            requestNew(URL_TECHNOLOGY, true, false);
        }
        if (HttpInfo.funNum != 0) {
            requestNew(URL_FUN, true, false);
        }
        if (HttpInfo.militaryNum != 0) {
            requestNew(URL_MILITARY, true, false);
        }
        if (HttpInfo.itNum != 0) {
            requestNew(URL_IT, true, false);
        }
        if (HttpInfo.footballNum != 0) {
            requestNew(URL_FOOTBALL, true, false);
        }
        if (HttpInfo.nbaNum != 0) {
            requestNew(URL_NBA, true, false);
        }
    }

    /**
     * 获取自定义关键字新闻
     */
    private void getCustomNews() {
        if (TagInfo.customTag.equals("")) {
            Toast.makeText(NewsActivity.this, "您还未设置关键字", Toast.LENGTH_LONG).show();
            return;
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature;
        try {
            signature = MD5Util.getMD5Str(HttpInfo.SecretKeyValue + timestamp + HttpInfo.AccessKeyValue);
        } catch (Exception e) {
            Toast.makeText(NewsActivity.this, "数据加密错误", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }
        String searchURL = "https://api.xinwen.cn/news/search?q=" + TagInfo.customTag + "&size=" + HttpInfo.NUM + "&signature=" + signature + "&order=relevance&timestamp=" + timestamp +"&access_key=fFZK5gezvwnEZ8CV";
        requestNew(searchURL, false, true);
        refreshLayout.setRefreshing(true);
    }
}

package com.sjh.news.Util;

import android.content.Context;
import android.widget.Toast;

import com.sjh.news.Activity.NewsActivity;
import com.sjh.news.Domain.NewsListJson;
import com.sjh.news.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 25235 on 2018/7/11.
 */

public class HttpUtil {
    private String httpUrl;
    private Context context;

    public HttpUtil(String httpUrl, Context context) {
        this.httpUrl = httpUrl;
        this.context = context;
    }

    //网络请求
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address).build();
        client.newCall(request).enqueue(callback);
    }


}

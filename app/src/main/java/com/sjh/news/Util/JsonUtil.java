package com.sjh.news.Util;


import com.google.gson.Gson;
import com.sjh.news.Domain.NewsListJson;

/**
 * Created by 25235 on 2018/7/11.
 */

public class JsonUtil {

    public static NewsListJson jsonToNewsList(String json) {
        Gson newsListJsonGson = new Gson();
        return newsListJsonGson.fromJson(json, NewsListJson.class);
    }
}

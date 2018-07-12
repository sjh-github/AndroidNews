package com.sjh.news.Util;

import com.sjh.news.Activity.NewsActivity;
import com.sjh.news.Domain.News;
import com.sjh.news.Domain.NewsJson;
import com.sjh.news.Domain.NewsListJson;
import com.sjh.news.StaicInfo.HttpInfo;
import com.sjh.news.StaicInfo.NewsInfo;
import java.util.List;

/**
 * Created by 25235 on 2018/7/11.
 */

public class NewsDataUtil {
    public static void addNews(NewsListJson newsListJson) {
        HttpInfo.code = newsListJson.getCode();
        HttpInfo.msg = newsListJson.getMsg();
        List<NewsJson> newsJsonList =  newsListJson.getNewsList();
        NewsJson newsJson = null;
        News news = null;
        for (int i = 0; i < newsJsonList.size(); i++) {
            newsJson = newsJsonList.get(i);
            news = new News(newsJson.getUrl(), newsJson.getTitle(), newsJson.getDescription(), newsJson.getPicUrl());
            NewsInfo.newsArrayList.add(news);
        }

        System.out.println("*****Size:" + NewsInfo.newsArrayList.size());
    }
}

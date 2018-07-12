package com.sjh.news.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 25235 on 2018/7/11.
 */

public class NewsListJson {
    private int code;

    private String msg;

    @SerializedName("newslist")
    private List<NewsJson> newsList ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsJson> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsJson> newsList) {
        this.newsList = newsList;
    }
}

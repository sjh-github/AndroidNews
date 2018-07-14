package com.sjh.news.Domain.fx;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 25235 on 2018/7/14.
 */

public class DataJson {
    private Integer count;
    private String first_id;
    private String last_id;
    @SerializedName("news")
    private List<NewsJson> newsJson;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFirst_id() {
        return first_id;
    }

    public void setFirst_id(String first_id) {
        this.first_id = first_id;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public List<NewsJson> getNewsJson() {
        return newsJson;
    }

    public void setNewsJson(List<NewsJson> newsJson) {
        this.newsJson = newsJson;
    }
}

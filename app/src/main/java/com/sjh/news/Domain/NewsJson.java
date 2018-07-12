package com.sjh.news.Domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 25235 on 2018/7/11.
 */

public class NewsJson {
    @SerializedName("ctime")
    private String time;

    private String title;

    private String description;

    private String picUrl;

    private String url;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

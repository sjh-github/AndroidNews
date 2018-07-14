package com.sjh.news.Domain.fx;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 25235 on 2018/7/14.
 */

public class NewsJson {
    private String title;
    private String source;
    private Integer hot_index;
    @SerializedName("thumbnail_img")
    private List<String> imgUrl;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getHot_index() {
        return hot_index;
    }

    public void setHot_index(Integer hot_index) {
        this.hot_index = hot_index;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

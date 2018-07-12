package com.sjh.news.Domain;

/**
 * Created by 25235 on 2018/7/10.
 */

public class News {
    private String url;
    private String newsTitle;
    private String newsResource;
    private String newsImageUrl;

    public News() {

    }

    public News(String url, String newsTitle, String newsResource, String newsImageUrl) {
        this.url = url;
        this.newsTitle = newsTitle;
        this.newsResource = newsResource;
        this.newsImageUrl = newsImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsResource() {
        return newsResource;
    }

    public void setNewsResource(String newsResource) {
        this.newsResource = newsResource;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "url='" + url + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsResource='" + newsResource + '\'' +
                ", newsImageUrl='" + newsImageUrl + '\'' +
                '}';
    }
}

package com.sjh.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjh.news.Domain.News;
import com.sjh.news.R;
import com.sjh.news.StaicInfo.NewsInfo;

/**
 * Created by 25235 on 2018/7/10.
 */

public class NewsListAdapter /*extends BaseAdapter*/ {

   /* private LayoutInflater mInflater;
    private News news;

    public NewsListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(NewsInfo.newsArrayList == null) {
            return NewsInfo.newsArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        if (i < 0 || i > NewsInfo.newsArrayList.size() - 1) {
            return null;
        }
        return NewsInfo.newsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newsView =  mInflater.inflate(R.layout.adapter_news, null);
        ImageView newsImage = newsView.findViewById(R.id.newsImage);
        TextView newsTitle = newsView.findViewById(R.id.newsTitle);
        TextView newsRes = newsView.findViewById(R.id.newsRes);

        News news = (News) getItem(i);
        newsTitle.setText(news.getNewsTitle());
        newsRes.setText(news.getNewsResource());
        //图片处理

        return newsView;
    }*/
}

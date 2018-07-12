package com.sjh.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjh.news.Domain.News;
import com.sjh.news.R;

import java.util.List;

/**
 * Created by 25235 on 2018/7/11.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private int resourceID;

    public NewsAdapter(Context context, int resource, List<News> newsList) {
        super(context, resource, newsList);
        resourceID = resource;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        News news = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (contentView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleText = view.findViewById(R.id.newsTitle);
            viewHolder.titlePic = view.findViewById(R.id.newsImage);
            viewHolder.titleDescr = view.findViewById(R.id.newsRes);
            view.setTag(viewHolder);
        } else {
            view = contentView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(getContext()).load(news.getNewsImageUrl()).into(viewHolder.titlePic);
        viewHolder.titleText.setText(news.getNewsTitle());
        viewHolder.titleDescr.setText(news.getNewsResource());
        return view;
    }


    public class ViewHolder{
        TextView titleText;
        TextView titleDescr;
        ImageView titlePic;
    }

}

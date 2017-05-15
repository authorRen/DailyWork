package com.caiyi.dailywork.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caiyi.dailywork.data.ItemArticle;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RZQ on 2017/5/10.
 */

public class HeaderAdapter extends PagerAdapter {

    private Context context;
    private List<ItemArticle> articles;
    private List<SimpleDraweeView> images = new ArrayList<>();

    public HeaderAdapter(Context mContext, List<ItemArticle> articles) {
        this.context = mContext;
        if (articles == null || articles.size() == 0) {
            this.articles = new ArrayList<>();
        } else {
            this.articles = articles;
        }

        for (int i = 0; i < articles.size(); i++) {
            SimpleDraweeView image = new SimpleDraweeView(context);
            Uri uri = Uri.parse(articles.get(i).getImageUrl());
            image.setImageURI(uri);
            images.add(image);
        }
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(images.get(position));
        return images.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

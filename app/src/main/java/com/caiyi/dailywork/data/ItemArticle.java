package com.caiyi.dailywork.data;

/**
 * Created by RZQ on 2017/5/10.
 */

public class ItemArticle {
    /** 新闻的id */
    private int index;
    /** 新闻里的图片 url */
    private String imageUrl;

    public ItemArticle(int index, String imageUrl) {
        this.index = index;
        this.imageUrl = imageUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ItemArticle{" +
                "index=" + index +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

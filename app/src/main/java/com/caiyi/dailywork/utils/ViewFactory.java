package com.caiyi.dailywork.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;

import com.caiyi.dailywork.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * SimpleDraweeView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取SimpleDraweeView视图的同时加载显示url
	 */
	public static SimpleDraweeView getImageView(Context context, String url) {
		SimpleDraweeView imageView = (SimpleDraweeView) LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		Uri uri = Uri.parse(url);
		imageView.setImageURI(uri);
		return imageView;
	}
}

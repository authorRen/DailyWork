package com.caiyi.dailywork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/4/20.
 */

public class DiscoveryListAdapter extends BaseListAdapter<String> {

    private final Context mContext;
    /**
     * Custructor
     *
     * @param inflater LayoutInflater
     */
    public DiscoveryListAdapter(LayoutInflater inflater) {
        super(inflater);
        mContext = getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item, viewGroup, false);
        }
        TextView textView = ViewHolder.get(convertView, R.id.tv_item);

        final String text = getAllDatas().get(position);
        if (text != null) {
            textView.setText(text);
        }
        return convertView;
    }
}

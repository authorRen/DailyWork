package com.caiyi.dailywork.adapter;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caiyi.dailywork.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by RZQ on 2017/5/24.
 */

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private HashMap<String, List<String>> dataSet;
    private List<String> parentList;

    public MyExpandableListViewAdapter(Context mContext, HashMap<String, List<String>> dataSet, List<String> parentList) {
        this.mContext = mContext;
        this.dataSet = dataSet;
        this.parentList = parentList;
    }

    /**
     * 获得父项的数量
     * @return
     */
    @Override
    public int getGroupCount() {
        return dataSet.size();
    }

    /**
     * 获得某个父项的子项数量
     * @return
     */
    @Override
    public int getChildrenCount(int parentPos) {
        return dataSet.get(parentList.get(parentPos)).size();
    }

    /**
     * 获得某个父项
     *
     * @return
     */
    @Override
    public Object getGroup(int parentPos) {
        return dataSet.get(parentList.get(parentPos));
    }

    /**
     * 获得某个父项的某个子项
     *
     * @return
     */
    @Override
    public Object getChild(int parentPos, int childPos) {
        return dataSet.get(parentList.get(parentPos)).get(childPos);
    }

    /**
     * 获得某个父项的id
     *
     * @return
     */
    @Override
    public long getGroupId(int groupPos) {
        return groupPos;
    }

    /**
     * 获得某个父项的某个子项的id
     * @return
     */
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    /**
     * 按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获得父项显示的view
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_city_choose, parent, false);
            holder = new GroupHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow_down);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        String title = parentList.get(groupPosition);
        holder.title.setText(title);
        if (isExpanded) {
            holder.arrow.setImageResource(R.drawable.gjj_arrow_up);
        } else {
            holder.arrow.setImageResource(R.drawable.gjj_arrow_down);
        }
        return convertView;
    }

    /**
     * 获得子项显示的view
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String data = dataSet.get(parentList.get(groupPosition)).get(childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_city_choose_inner, parent, false);
        }
        TextView content = ViewHolder.get(convertView, R.id.chose_item);
        content.setText(data);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了" + data, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    /**
     *  子项是否可选中，如果需要设置子项的点击事件，需要返回true
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupHolder {
        //标题
        TextView title;
        //箭头
        ImageView arrow;
    }
}

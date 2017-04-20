package com.caiyi.dailywork.adapter;

import android.text.Spannable;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by RZQ on 2017/4/20.
 */

public class ViewHolder {
    /** Constructor */
    private ViewHolder() {}

    /**
     *
     * 在adapter 的getView()方法中可以直接以如下方式使用：
     *
     * <pre>
     * if (convertView == null) {
     *     convertView = LayoutInflater.from(context).inflate(R.layout.list_test, parent, false);
     * }
     *
     * ImageView ivLogo = ViewHolder.get(convertView, R.id.logo);
     *   ...
     * </pre>
     *
     * @param view
     *            convertView
     * @param resId
     *            资源id
     * @param <T>
     *            resId对应的View
     * @return resId对应的View
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int resId) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(resId);
        if (childView == null) {
            childView = view.findViewById(resId);
            viewHolder.put(resId, childView);
        }
        return (T) childView;
    }
}

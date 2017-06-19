package com.caiyi.dailywork.compant;

import java.util.ArrayList;
import java.util.List;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.cyclelib.CycleFragment;
import com.caiyi.dailywork.data.ADInfo;
import com.caiyi.dailywork.utils.Utilty;
import com.caiyi.dailywork.utils.ViewFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Toast;

/**
 * Created by RZQ on 2017/5/8.
 */

public class RecycleActivity extends BaseActivity {

    private List<SimpleDraweeView> views = new ArrayList<>();
    private List<ADInfo> infos = new ArrayList<>();
    private CycleFragment cycleViewPager;

    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"
         };

    private ViewPager mViewPager;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        /** fragment实现轮播图 */
//        initialize();
        /** 轮播图效果 */
        initView();
    }

    @SuppressLint("NewApi")
    private void initialize() {

        cycleViewPager = (CycleFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        if (infos.size() == 1) {
            cycleViewPager.setCycle(false);
            cycleViewPager.setScrollable(false);
        } else {
            cycleViewPager.setCycle(true);
            cycleViewPager.setScrollable(true);
        }

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        if (infos.size() == 1) {
            cycleViewPager.setWheel(false);
        } else {
            cycleViewPager.setWheel(true);
        }

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
    }

    private CycleFragment.ImageCycleViewListener mAdCycleViewListener = new CycleFragment.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(RecycleActivity.this,
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }
        }

    };

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_banner);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }

        for (int i = 0; i < infos.size(); i++) {
            SimpleDraweeView view = (SimpleDraweeView) constructItemView(infos.get(i));
            if (i == infos.size() - 1) {
            }
            views.add(view);
        }

        adapter = new MyAdapter(views);
        mViewPager.setAdapter(adapter);

    }

    /** 构造每一个item的视图 */
    private View constructItemView(final ADInfo adInfo) {
        SimpleDraweeView itemView = (SimpleDraweeView) getLayoutInflater().inflate(R.layout.view_banner, null);
        String url = adInfo.getUrl();
        Uri uri = Uri.parse(url);
        itemView.setImageURI(uri);
//        itemView.setPadding(Utilty.dip2px(this, 9f), 0, 0, 0);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecycleActivity.this,adInfo.getContent(),Toast.LENGTH_LONG).show();
            }
        });
        return itemView;
    }

    private class MyAdapter extends PagerAdapter {

        private List<SimpleDraweeView> views;

        public MyAdapter(List<SimpleDraweeView> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;    //这行代码很重要，它用于判断你当前要显示的页面
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (views == null || views.size() < 0) return null;
            //对viewPager页号求模取出views列表中要显示的项
//            position %= views.size();
//            java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
            SimpleDraweeView view = views.get(position);
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(views.get(position));
            Log.d("tag",String.valueOf(position));
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
}

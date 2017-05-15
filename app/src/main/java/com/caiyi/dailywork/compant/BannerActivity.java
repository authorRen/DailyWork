package com.caiyi.dailywork.compant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.adapter.HeaderAdapter;
import com.caiyi.dailywork.data.ItemArticle;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by RZQ on 2017/5/10.
 */

public class BannerActivity extends BaseActivity {

    private static final int UPTATE_VIEWPAGER = 0;

    //轮播的最热新闻图片
    ViewPager vpHottest;
    //轮播图片下面的小圆点
    LinearLayout llHottestIndicator;
    //存储的参数
    private String mParam;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;
    private ImageView[] mBottomImages;//底部只是当前页面的小圆点

    private Timer timer = new Timer(); //为了方便取消定时轮播，将 Timer 设为全局

    //定时轮播图片，需要在主线程里面修改UI
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
                        vpHottest.setCurrentItem(msg.arg1);
                    } else {
                        /*false 当从末页调到首页时，不显示翻页动画效果*/
                        vpHottest.setCurrentItem(msg.arg1, false);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initView();

        new ImageTask().execute();
    }

    private void initView() {
        vpHottest = (ViewPager) findViewById(R.id.vp_banner);
        llHottestIndicator = (LinearLayout) findViewById(R.id.ll_indicator);
    }

    private void setUpViewPager(final List<ItemArticle> headerArticles) {
        HeaderAdapter imageAdapter = new HeaderAdapter(this, headerArticles);
        vpHottest.setAdapter(imageAdapter);

        //创建底部指示位置的导航栏
        mBottomImages = new ImageView[headerArticles.size()];

        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.gjj_circle_dot_red);
            } else {
                imageView.setBackgroundResource(R.drawable.gjj_circle_dot_white_2);
            }

            mBottomImages[i] = imageView;
            //把中指示作用的原点图片加入底部的视图
            llHottestIndicator.addView(mBottomImages[i]);
        }

        vpHottest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //图片左右滑动时候，将当前页的圆点图片设为选中状态
            @Override
            public void onPageSelected(int position) {
                // 一定几个图片，几个圆点，但注意是从0开始的
                int total = mBottomImages.length;
                for (int j = 0; j < total; j++) {
                    if (j == position) {
                        mBottomImages[j].setBackgroundResource(R.drawable.gjj_circle_dot_red);
                    } else {
                        mBottomImages[j].setBackgroundResource(R.drawable.gjj_circle_dot_white_2);
                    }
                }
                //设置全局变量，currentIndex为选中图标的 index
                autoCurrIndex = position;
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        }
        );
        // 设置自动轮播图片，1s后执行，周期是2s
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == headerArticles.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 1000, 2000);
    }


    class ImageTask extends AsyncTask<String, Void, List<ItemArticle>> {
        @Override
        protected List<ItemArticle> doInBackground(String... params) {
            List<ItemArticle> articles = new ArrayList<>();
            articles.add(
                    new ItemArticle(1123, "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg"));
//            articles.add(
//                    new ItemArticle(1123, "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg"));
//            articles.add(
//                    new ItemArticle(1123, "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg"));
//            articles.add(
//                    new ItemArticle(1123, "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg"));
            return articles;
        }

        @Override
        protected void onPostExecute(List<ItemArticle> articles) {
            //这儿的 是 url 的集合
            super.onPostExecute(articles);
            setUpViewPager(articles);

        }
    }

}

package com.caiyi.dailywork.compant;

import java.util.ArrayList;
import java.util.List;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.cyclelib.CycleFragment;
import com.caiyi.dailywork.data.ADInfo;
import com.caiyi.dailywork.utils.ViewFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by RZQ on 2017/5/8.
 */

public class RecycleActivity extends BaseActivity {

    private List<SimpleDraweeView> views = new ArrayList<>();
    private List<ADInfo> infos = new ArrayList<>();
    private CycleFragment cycleViewPager;

    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg"
//            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
//            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
         };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        initialize();
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
}

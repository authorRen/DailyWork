package com.caiyi.dailywork.compant;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.data.RiseNumber;
import com.caiyi.dailywork.ui.EasyPickerView;

import java.util.ArrayList;

/**
 * 数字动态增加
 *
 * Created by RZQ on 2017/5/18.
 */

public class PictureActivity extends BaseActivity {

    private TextView rnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        setupViews();
        initView();
    }

    private void setupViews() {
        // 获取到RiseNumberTextView对象
        rnTextView = (TextView) findViewById(R.id.risenumber_textview);
        final RiseNumber riseNumber = new RiseNumber(rnTextView, 1500, 0, (float) 2490.20);
        riseNumber.setOnEndListener(new RiseNumber.EndListener() {
            @Override
            public void onEndFinish() {
                Toast.makeText(PictureActivity.this, "数据增长完毕...", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(riseNumber.isRunning()){
                    Toast.makeText(PictureActivity.this, "数字还没增长完，请稍候尝试...", Toast.LENGTH_SHORT).show();
                }else{
                    // 开始播放动画
                    riseNumber.start();
                }

            }
        });
    }

    private void initView() {
        ArrayList<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            dataList.add("" + i);

        EasyPickerView epv = (EasyPickerView) findViewById(R.id.epv);
        epv.setDataList(dataList);
    }
}

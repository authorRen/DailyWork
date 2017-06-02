package com.caiyi.dailywork.compant;


import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.ui.WheelView;
import com.caiyi.dailywork.utils.NotificationsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by RZQ on 2017/5/26.
 */

public class DemoActivity extends BaseActivity {

    private WheelView mWvData;
    private WheelView mWvHour;
    private TextView mSystem;
    private TextView mLocal;

    private boolean enabled;

    private List<String> mDay = new ArrayList<>();
    private List<String> mHour = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_demo);

        getPermission();
        initToolbar();
        initView();
        initTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (enabled) {
            //通知权限打开状态
            mSystem.setVisibility(View.GONE);
            mLocal.setVisibility(View.VISIBLE);
        } else {
            //通知权限关闭状态
            mSystem.setVisibility(View.VISIBLE);
            mLocal.setVisibility(View.GONE);
        }
    }

    /**
     * 获取通知栏权限是否开启
     */
    private void getPermission() {
        enabled = NotificationsUtils.isNotificationEnabled(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("公积金账单提醒");
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mWvData = (WheelView) findViewById(R.id.wv_data);
        for (int i = 1; i <= 28; i++) {
            mDay.add(String.valueOf(i));
        }
        mWvData.updateData(mDay);
        mWvHour = (WheelView) findViewById(R.id.wv_hour);
        for (int i = 0; i <= 24; i++) {
            mHour.add(String.valueOf(i) + ":00");
        }
        mWvHour.updateData(mHour);
        mSystem = (TextView) findViewById(R.id.tv_system);
        mLocal = (TextView) findViewById(R.id.tv_local);

        setViewClickListeners(R.id.btn_sure, R.id.tv_system);
    }

    /**
     * 设置默认显示的时间
     */
    private void initTime() {
        mWvData.setCurrentPos(26); //默认27号
        mWvHour.setCurrentPos(17); //默认下午5点
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                setAlaem();
                break;
            case R.id.tv_system:
                skipSystem();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转系统界面
     */
    private void skipSystem() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    /**
     * 获取选中的时间,设置闹钟
     */
    private void setAlaem() {
        String data = mWvData.getCurrentText(); //获取日期
        String hour = mWvHour.getCurrentText(); //获取小时

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


    }
}

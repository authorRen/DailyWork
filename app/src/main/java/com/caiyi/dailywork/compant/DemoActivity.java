package com.caiyi.dailywork.compant;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
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

    private static final String TAG = "DemoActivity";

    private WheelView mWvData;
    private WheelView mWvHour;
    private TextView mSystem;
    private CheckedTextView mLocal;
    private TextView mIntroduce;
    private Button mBtnEnsure;

    private boolean enabled;

    private List<String> mDay = new ArrayList<>();
    private List<String> mHour = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_demo);

        initToolbar();
        initView();
        initTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enabled = NotificationsUtils.isNotificationEnabled(this);
        if (enabled && !mLocal.isChecked()) {
            //通知权限打开状态
            mSystem.setVisibility(View.GONE);
            mLocal.setVisibility(View.VISIBLE);
            openSystem();
            setAlarm();
        } else if (enabled && mLocal.isChecked()){
            mSystem.setVisibility(View.GONE);
            mLocal.setVisibility(View.VISIBLE);
            closeState();
        } else {
            //通知权限关闭状态
            mSystem.setVisibility(View.VISIBLE);
            mLocal.setVisibility(View.GONE);
            closeSystem();
        }
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
        mLocal = (CheckedTextView) findViewById(R.id.tv_local);
        mIntroduce = (TextView) findViewById(R.id.tv_introduce);
        mBtnEnsure = (Button) findViewById(R.id.btn_sure);

        enabled = NotificationsUtils.isNotificationEnabled(this);

        setViewClickListeners(R.id.btn_sure, R.id.tv_system, R.id.tv_local);
    }

    /**
     * 系统通知关闭状态
     */
    private void closeSystem() {
        mIntroduce.setText(R.string.closeIntroduce);
        mBtnEnsure.setBackgroundResource(R.drawable.bg_unensure);
        mBtnEnsure.setClickable(false);
    }

    /**
     * 系统通知打开状态
     */
    private void openSystem() {
        mIntroduce.setText(R.string.openIntroduce);
        mBtnEnsure.setBackgroundResource(R.drawable.bg_ensure);
        mBtnEnsure.setClickable(true);
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
                setAlarm();
                /**　处理确认后UI显示　*/
               showSureDialog();
                break;
            case R.id.tv_system:
                skipSystem();
                break;
            case R.id.tv_local:
                mLocal.toggle();
                if (mLocal.isChecked()) {
                    closeState();
                } else {
                    openState();
                }
                break;
            default:
                break;
        }
    }

    private void showSureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        RelativeLayout inflate = (RelativeLayout) getLayoutInflater().inflate(R.layout.show_dialog, null);
        TextView success = (TextView) inflate.findViewById(R.id.set_success);
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });
        builder.setView(inflate);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 通知打开状态
     */
    private void openState() {
        mLocal.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gjj_eye_2, 0);
        mBtnEnsure.setBackgroundResource(R.drawable.bg_ensure);
        mBtnEnsure.setClickable(true);
        setAlarm();
        Log.i(TAG, "openState ");
    }

    /**
     * 通知关闭方法
     */
    private void closeState() {
        cancelAlarm();
        Log.i(TAG, "closeState ");
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
    private void setAlarm() {
        /** 处理设置闹钟逻辑 */
        int data = mWvData.getCurrentPos() + 1; //获取日期
        int hour = mWvHour.getCurrentPos(); //获取小时

        Intent intent = new Intent(this, DateChangeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 40);
        Log.i(TAG, "" + calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE,1);
        Log.i(TAG, "" + calendar.getTimeInMillis());

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + 5 * 1000, 5 * 1000, sender);
    }

    private void cancelAlarm() {
        mLocal.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gjj_eye_close_2, 0);
        mBtnEnsure.setBackgroundResource(R.drawable.bg_unensure);
        mBtnEnsure.setClickable(false);
        Intent intent = new Intent(this, DateChangeReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this,
                0, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }
}

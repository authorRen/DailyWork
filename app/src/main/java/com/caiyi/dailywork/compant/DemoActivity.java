package com.caiyi.dailywork.compant;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.ui.WheelView;
import com.caiyi.dailywork.utils.SPUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RZQ on 2017/5/26.
 */

public class DemoActivity extends BaseActivity {

    private TextView mTvTime;
    private Button mBtnGetTime;
    private WheelView mWvData;
    private WheelView mWvHour;
    private WheelView mWvMinute;

    private String mData;
    private String mHour;
    private String mMinute;

    private TextView mTvChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_demo);

        initView();
    }

    private void initView() {
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvChoose = (TextView) findViewById(R.id.tv_choose);
        mWvData = (WheelView) findViewById(R.id.wv_data);
        mWvHour = (WheelView) findViewById(R.id.wv_hour);
        mWvMinute = (WheelView) findViewById(R.id.wv_minute);
        mBtnGetTime = (Button) findViewById(R.id.btn_getTime);
        mBtnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSystemTime();

                getChooseTime();

                long time = System.currentTimeMillis();

                Calendar mCalendar = Calendar.getInstance();

                mCalendar.setTimeInMillis(time);

                int mHour = mCalendar.get(Calendar.MINUTE);

                showToast(mHour+"");

            }

        });
    }

    private void getSystemTime() {
        long time = System.currentTimeMillis();

        Date date = new Date(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

        String format = simpleDateFormat.format(date);

        mTvTime.setText(format);

    }

    private void getChooseTime() {
        mData = mWvData.getCurrentText();
        mHour = mWvHour.getCurrentText();
        mMinute = mWvMinute.getCurrentText();

        mTvChoose.setText(mData + "日" + mHour + "小时" + mMinute + "分钟");

//        SPUtil.putString("data", mData);
//        SPUtil.putString("hour", mHour);
//        SPUtil.putString("minute", mMinute);

        Intent intent = new Intent(this, DateChangeReceiver.class);
        intent.putExtra("data", mData);
        intent.putExtra("hour", mHour);
        intent.putExtra("minute", mMinute);
        intent.setAction(Intent.ACTION_TIME_TICK);
        sendBroadcast(intent);

    }

}

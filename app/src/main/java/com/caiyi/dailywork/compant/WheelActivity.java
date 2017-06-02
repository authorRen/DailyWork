package com.caiyi.dailywork.compant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.caiyi.dailywork.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by RZQ on 2017/5/31.
 */

public class WheelActivity extends Activity {

    private TextView tvTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        tvTime = (TextView) findViewById(R.id.tv_wheelTime);

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) { //选中事件的回调
                tvTime.setText(date.toString());
            }
        }).build();

        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }
}

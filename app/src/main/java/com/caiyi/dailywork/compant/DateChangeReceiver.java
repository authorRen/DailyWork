package com.caiyi.dailywork.compant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.utils.SPUtil;

import java.util.Calendar;

/**
 * Created by RZQ on 2017/5/27.
 */

class DateChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "DateChangeReceiver";

    private static final int NOTIFICATION_FLAG = 1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 时间发生变化");
        if (!intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            return;
        }

        long time = System.currentTimeMillis();

        Calendar mCalendar = Calendar.getInstance();

        mCalendar.setTimeInMillis(time);

        int mMinute = mCalendar.get(Calendar.MINUTE);

        int mHour = mCalendar.get(Calendar.HOUR);

        int mDate = mCalendar.get(Calendar.DAY_OF_MONTH);

        if (mMinute == 58 && mHour == 4 && mDate == 31) {
            Intent startIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startIntent, 0);
            //通过notification.builder来创建通知，注意API Level
            //API 16之后才支持
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.logo)
                    .setTicker("您有新消息注意查收!")
                    .setContentTitle("有鱼公积金管家")
                    .setContentText("你的公积金很近没有更新了，点击公积金余额刷新，赶快看一下公积金余额涨了没有吧！")
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL; //用户点击通知后立即清除
            // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
            manager.notify(NOTIFICATION_FLAG, notification);
        }

    }
}

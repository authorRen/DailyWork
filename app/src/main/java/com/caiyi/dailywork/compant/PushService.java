package com.caiyi.dailywork.compant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.caiyi.dailywork.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by RZQ on 2017/5/26.
 */

public class PushService extends Service {

    private static final String TAG = "PushService";

    private static final int NOTIFICATION_ID = 1000;

    //清除通知
    public static void clearAllNotification() {
        NotificationManager manager = (NotificationManager) MainActivity.getTopActivity()
                .getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    //添加通知
    public static void addNotification(int delayTime, String tickerText, String contentTitle, String contentText) {
        Intent intent = new Intent(MainActivity.getTopActivity(), PushService.class);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("tickerText", tickerText);
        intent.putExtra("contentTitle", contentTitle);
        intent.putExtra("contentText", contentText);
        MainActivity.getTopActivity().startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "addNotification------onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (intent.getAction().equals("NOTIFICATION")) {
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    NotificationManager manager = (NotificationManager) PushService.this.getSystemService(NOTIFICATION_SERVICE);
                    Intent skip = new Intent(getApplication(), DemoActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, skip, 0);
                    Notification notification = new Notification.Builder(PushService.this)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("有鱼公积金管家")
                            .setTicker("你有新消息啦！")
                            .setContentIntent(pendingIntent)
                            .setContentText("你的公积金很近没有更新了，点击公积金余额刷新，赶快看一下公积金余额涨了没有吧！")
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .build();

                    manager.notify(NOTIFICATION_ID, notification);
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "===========destroy=======");
        super.onDestroy();
    }
}

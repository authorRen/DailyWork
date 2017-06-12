package com.caiyi.dailywork.compant;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
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

    static Timer timer = null;

    //清除通知
    public static void cleanAllNotification() {
        NotificationManager manager = (NotificationManager) DialyApplication.getmAppContext()
                .getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //添加通知
    public static void addNotification(int delayTime, String tickerText, String contentTitle, String contentText) {
        Intent intent = new Intent(DialyApplication.getmAppContext(), PushService.class);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("tickerText", tickerText);
        intent.putExtra("contentTitle", contentTitle);
        intent.putExtra("contentText", contentText);
        DialyApplication.getmAppContext().startService(intent);
    }

    public void onCreate() {
        Log.e("addNotification", "===========create=======");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        long period = 24 * 60 * 60 * 1000; //24小时一个周期
        int delay = intent.getIntExtra("delayTime", 0);
        if (null == timer) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                NotificationManager mn= (NotificationManager) PushService.this.getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(PushService.this);
                Intent it = new Intent(PushService.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(PushService.this, 0, it, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Notification notification = builder.setAutoCancel(true)
                            .setContentTitle(intent.getStringExtra("contentTitle"))
                            .setContentText(intent.getStringExtra("contentText"))
                            .setTicker(intent.getStringExtra("tickerText"))
                            .setSmallIcon(R.drawable.logo)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .build();
                    mn.notify((int)System.currentTimeMillis(),notification);
                }

            }
        }, delay, period);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("addNotification", "===========destroy=======");
    }
}

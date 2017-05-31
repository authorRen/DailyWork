package com.caiyi.dailywork.compant;

import android.app.AlarmManager;
import android.app.ExpandableListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caiyi.dailywork.R;

import java.util.Date;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvResolution;
    /** 获取手机屏幕分辨率相关类*/
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        rigisterBroadCast();

        openTimeService();

//        openService();

        setViewClickListeners(R.id.btn_press, R.id.btn_span,
                R.id.btn_toolbar, R.id.btn_floating, R.id.btn_discovery,
                R.id.btn_broadcast, R.id.btn_notification, R.id.btn_recycle,
                R.id.btn_banner, R.id.btn_picture, R.id.btn_shape, R.id.btn_glide,
                R.id.btn_ExpandableListView, R.id.btn_Demo);

    }

    /**
     * 启动实时监听时间变化的服务
     */
    private void openTimeService() {
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);
    }

    /**
     * 开启定时发送通知的服务
     */
    private void openService() {
        Intent intent = new Intent(this, PushService.class);
        intent.setAction("NOTIFICATION");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;
        long triggerAtMillis  = System.currentTimeMillis() + 10 * 1000;
        long intervalMillis = 10 * 1000;
        alarmManager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pendingIntent);
    }

    private void initView() {
        tvResolution = (TextView) findViewById(R.id.tv_resolution);

    }

    /**
     * 动态注册广播
     */
    private void rigisterBroadCast() {
        MyReciver myReciver = new MyReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.intent.action.MY_RECIVER");

        registerReceiver(myReciver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_press:
                getResolution();
                break;
            case R.id.btn_floating:
                openActivity(FloatingActivity.class);
                break;
            case R.id.btn_span:
                openActivity(SpannedActivity.class);
                break;
            case R.id.btn_toolbar:
                openActivity(ToolbarActivity.class);
                break;
            case R.id.btn_discovery:
                openActivity(DiscoveryActivity.class);
                break;
            case R.id.btn_broadcast:
                send();
                break;
            case R.id.btn_notification:
                sendNotification();
                break;
            case R.id.btn_recycle:
                openActivity(RecycleActivity.class);
                break;
            case R.id.btn_banner:
                openActivity(BannerActivity.class);
                break;
            case R.id.btn_picture:
                openActivity(PictureActivity.class);
                break;
            case R.id.btn_shape:
                openActivity(ShapeActivity.class);
                break;
            case R.id.btn_glide:
                openActivity(GlideActivity.class);
                break;
            case R.id.btn_ExpandableListView:
                openActivity(ExpandableListViewActivity.class);
                break;
            case R.id.btn_Demo:
                openActivity(DemoActivity.class);
            default:
                break;
        }

    }

    /**
     * 发送通知
     */
    public void sendNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;//通知图标
        Intent openIntent = new Intent(this, DiscoveryActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT); //当点击消息时就会向系统发送openItent意图
        //新建一个通知
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("我的标题")
                .setContentIntent(contentIntent)
                .setShowWhen(true)
                .setSmallIcon(icon)
                .build();
        notification.defaults = Notification.DEFAULT_SOUND; //发出默认的声音
        notification.flags |= Notification.FLAG_AUTO_CANCEL; //点击通知后自动清除
//        notification.setLatestEventInfo(this, "标题", "我是内容", contentIntent);

        manager.notify(0, notification);
    }

    /**
     * 执行发送广播
     */
    private void send() {
        Intent intent = new Intent("com.intent.action.MY_RECIVER");
        intent.putExtra("data","发送了一个广播");
        sendBroadcast(intent);

    }

    /**
     * 获取手机屏幕的分辨率
     */
    private void getResolution() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取手机屏幕的宽度和高度像素单位为px
        String str = getResources().getString(R.string.resolution) + dm.widthPixels  + "*" + dm.heightPixels;
        tvResolution.setText(str);
    }
}

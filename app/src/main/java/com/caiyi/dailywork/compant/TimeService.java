//package com.caiyi.dailywork.compant;
//
//import android.app.Service;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
///**
// * 通过了解我们知道注册ACTION_TIME_TICK广播接收器可以监听系统事件改变，但是
// * 查看SDK发现ACTION_TIME_TICK广播事件只能动态注册
// *
// * Created by RZQ on 2017/5/27.
// */
//
//public class TimeService extends Service {
//
//    private static final String TAG = "TimeService";
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.i(TAG, "onCreate: 后台进程被创建");
//
//        DateChangeReceiver receiver = new DateChangeReceiver();
//        registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.i(TAG, "onStartCommand: 后台进程");
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy: 后台进程被销毁");
//    }
//}

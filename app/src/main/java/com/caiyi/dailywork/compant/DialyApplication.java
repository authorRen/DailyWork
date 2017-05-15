package com.caiyi.dailywork.compant;

import android.app.Application;
import android.content.Context;

import com.caiyi.dailywork.utils.AppUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by RZQ on 2017/5/10.
 */

public class DialyApplication extends Application {
    /** GLOBAL_DEBUG */
    public static boolean GLOBAL_DEBUG = true;
    /** tag */
    public static final String TAG = "Application";
    /** 全局context */
    public static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        //解决多进程下Application#onCreate()执行多次的问题
        if (!isMainProcess()) {
            return;
        }
        mAppContext = getApplicationContext();

        Fresco.initialize(this);
    }

    /** 判断是否是主进程，当前子进程有：push、remote（百度定位）、LeakCanary*/
    private boolean isMainProcess() {
        return AppUtils.getCurrentProcesName(this).equals(getPackageName());
    }
}

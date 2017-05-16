package com.caiyi.dailywork.compant;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.caiyi.dailywork.BuildConfig;
import com.caiyi.dailywork.common.crash.CrashHandler;
import com.caiyi.dailywork.common.log.Logger;
import com.caiyi.dailywork.net.OkHttpUtils;
import com.caiyi.dailywork.utils.AppUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by RZQ on 2017/5/10.
 */

public class DialyApplication extends Application {
    /** GLOBAL_DEBUG */
    public static boolean GLOBAL_DEBUG = true;
    /** tag */
    public static final String TAG = "DialyApplication";
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
        /** 打印日志初始化 */
        Logger.init(TAG).showLog(true).hideThreadInfo().methodCount(1);
        MultiDex.install(this);
        initFresco();

        if (BuildConfig.DEBUG) {
            doOnlyInDebug();
        }
    }

    /**
     * 初始化Fresco图片库
     */
    private void initFresco() {
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, OkHttpUtils.getOkHttpClient()).build();
        Fresco.initialize(this, config);
    }

    /** 判断是否是主进程，当前子进程有：push、remote（百度定位）、LeakCanary*/
    private boolean isMainProcess() {
        return AppUtils.getCurrentProcesName(this).equals(getPackageName());
    }

    /**
     * 全局使用context
     *
     * @return context
     */
    public static Context getmAppContext() {
        return mAppContext;
    }

    /**
     * 只在DEBUG模式下有效
     */
    private void doOnlyInDebug() {
        //保存crash log信息
        final String logDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/000fundcrash/";
        CrashHandler.getInstance().init(logDir);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites()
                .detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog().penaltyDeath().build());
    }
}

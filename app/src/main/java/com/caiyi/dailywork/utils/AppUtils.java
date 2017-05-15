package com.caiyi.dailywork.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

/**
 * App相关工具类
 *
 * Created by RZQ on 2017/5/15.
 */

public class AppUtils {
    /** 获取当前进程名称 */
    public static String getCurrentProcesName(Context context) {
        if (context == null) {
            return "";
        }
        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}

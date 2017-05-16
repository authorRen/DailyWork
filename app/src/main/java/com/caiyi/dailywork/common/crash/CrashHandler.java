package com.caiyi.dailywork.common.crash;

import android.os.Build;


import com.caiyi.dailywork.common.log.Logger;
import com.caiyi.dailywork.utils.AppUtils;
import com.caiyi.dailywork.utils.DateTimeUtil;
import com.caiyi.dailywork.utils.ExtendUtil;
import com.caiyi.dailywork.utils.FileUtil;
import com.caiyi.dailywork.utils.IOUtil;
import com.caiyi.dailywork.utils.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

/**
 * 收集crash Log
 * created by HuoGuangXu at 2016/12/11
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public final String TAG = "CrashHandler";

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler INSTANCE;
    private String mLogDir;

    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param logDir  log保存目录
     */
    public void init(String logDir) {
        mLogDir = logDir;

        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        handleException(throwable);
        //交给系统处理，否则错误Log在IDE打不出来
        mDefaultHandler.uncaughtException(thread, throwable);
    }

    /**
     * 错误处理.收集错误信息、发送错误报告等
     */
    private void handleException(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        saveCrashInfo(throwable);
        //reportCrashLogs("");
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private boolean saveCrashInfo(final Throwable throwable) {
        if (!ExtendUtil.isSdCardAvailable() || StringUtil.isNullOrEmpty(mLogDir)) {
            return false;
        }

        String fileName = "crash_" + DateTimeUtil.formatDate(new Date(), "yyyy-MM-dd_HH-mm-ss")
                + "_" + System.currentTimeMillis() + ".log";
        final String fullPath = mLogDir + fileName;
        Logger.i("日志保存路径--->" + fullPath);

        File fileLog = FileUtil.createFile(fullPath);
        if (fileLog == null) {
            return false;
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(fullPath, false));
            printWriter.write(getDeviceInfo());
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            IOUtil.close(printWriter);
        }
    }

    private void reportCrashLogs(String logPath) {
        // TODO 发送错误报告到服务器
    }

    /**
     * 收集设备部分参数信息
     */
    private String getDeviceInfo() {
        return "\n************* Crash Log Head ****************" +
                "\nApp VersionName    : " + AppUtils.getAppInfo().getVersionName() +
                "\nApp VersionCode    : " + AppUtils.getAppInfo().getVersionCode() +
                "\nApp PackageName    : " + AppUtils.getAppInfo().getPackageName() +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nMANUFACTURER       : " + Build.MANUFACTURER +
                "\nModel              : " + Build.MODEL +
                "\nBRAND              : " + Build.BRAND +
                "\nDEVICE             : " + Build.DEVICE +
                "\n************* Crash Log Head ****************\n\n";
    }
}
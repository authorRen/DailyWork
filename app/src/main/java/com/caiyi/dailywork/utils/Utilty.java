package com.caiyi.dailywork.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.caiyi.dailywork.compant.DialyApplication;

/**
 * Created by RZQ on 2017/5/16.
 */

public class Utilty {

    /** sharedpreferences */
    private static SharedPreferences mSp;
    /** sp editor */
    private static SharedPreferences.Editor editor;
    /** 存储的文件名，存储少量用户信息，非安全性 */
    private static final String SHARE_FILE_NAME = "UserInfo";


    /**
     * 保存服务器与本地时间差
     *
     * @param delta   时间差(本地当前时间+时间差=实际服务器当前时间)
     */
    public static void setServerTimeDelta(long delta) {
        getUserInfoSp().edit().putLong("SERVER_TIME_DELTA", delta).commit();
    }

    /**
     * dip to px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // SUPPRESS CHECKSTYLE
    }

    public static SharedPreferences getUserInfoSp() {
        return DialyApplication.getmAppContext().getSharedPreferences(AppConfig.SHARED_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * check sp exists
     * @param context
     */
    private static void checkSp(Context context) {
        if (null == mSp) {
            mSp = context.getSharedPreferences(SHARE_FILE_NAME, Context.MODE_PRIVATE);
            editor = mSp.edit();
        }
    }

    /**
     * set sp data <br/>
     * 注意:<br/>
     * 1.连续保存的时候(eg: setSpData("key1","value1");setSpData("key2","value2")<br/>
     * 使用commit同步提交没有问题，但耗时。<br/>
     * 2.而使用apply连续提交时会高概率出现保存不成功的问题,<br/>
     * 使用getUserInfoSp().edit().putString("key1","value1").putInt("key2","value2").apply();可解决.
     * @param key
     * @param value
     */
    public static void setSpData(String key, String value) {
        getUserInfoSp().edit().putString(key, value).commit();
    }

    /**
     * set sp datad
     * @param context
     * @param key
     * @param value
     */
    public static void setSpData(Context context, String key, String value) {
        checkSp(context);
        editor.putString(key, value);
        editor.commit();

    }

    private static String getSpData(String key) {
        return getSpData(key, "");
    }

    /**
     * get sp data
     */
    public static String getSpData(String key, String defValue) {
        return getUserInfoSp().getString(key, defValue);
    }

    /**
     * get sp data
     *
     * @param context context
     * @param key     key
     * @return sp data
     */
    public static String getSpData(Context context, String key) {
        checkSp(context);
        return mSp.getString(key, "");
    }

    /**
     * get sp data
     *
     * @param context  context
     * @param key      key
     * @param defValue defValue
     * @return sp data
     */
    public static String getSpData(Context context, String key, String defValue) {
        checkSp(context);
        return mSp.getString(key, defValue);
    }

}

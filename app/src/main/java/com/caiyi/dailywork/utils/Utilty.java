package com.caiyi.dailywork.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.caiyi.dailywork.compant.DialyApplication;

/**
 * Created by RZQ on 2017/5/16.
 */

public class Utilty {

    /**
     * 保存服务器与本地时间差
     *
     * @param delta   时间差(本地当前时间+时间差=实际服务器当前时间)
     */
    public static void setServerTimeDelta(long delta) {
        getUserInfoSp().edit().putLong("SERVER_TIME_DELTA", delta).commit();
    }

    public static SharedPreferences getUserInfoSp() {
        return DialyApplication.getmAppContext().getSharedPreferences(AppConfig.SHARED_FILE_NAME, Context.MODE_PRIVATE);
    }
}

package com.caiyi.dailywork.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.caiyi.dailywork.compant.DialyApplication;

/**
 * 网络相关工具类
 *
 * Created by RZQ on 2017/6/22.
 */

public class NetWorkUtils {

    public NetWorkUtils() {
    }

    /**
     * 判断网络是否可用
     * @return
     */
    public static boolean isNetorkConnected() {
        ConnectivityManager manager = (ConnectivityManager) DialyApplication.getmAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 判断是否是wifi
     * @return
     */
    public static boolean isWifiAvailable() {
      return GlobalConstants.NETWORK_TYPE.TYPE_WIFI == getNetWorkType();
    }



    public static int getNetWorkType() {
        ConnectivityManager manager = (ConnectivityManager) DialyApplication.getmAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            int type = activeNetworkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return GlobalConstants.NETWORK_TYPE.TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return GlobalConstants.NETWORK_TYPE.TYPE_MOBILE;
            }
        }
        return GlobalConstants.NETWORK_TYPE.TYPE_UNKNOWN;
    }
}

package com.caiyi.dailywork.utils;

import android.text.TextUtils;

import java.text.NumberFormat;

/**
 * 数字操作工具类
 * Created by HuoGuangXu on 2016/9/14.
 */

public class NumberUtil {

    /**
     * 字符串转整型,转换出错返回-1
     */
    public static int getInt(String intString) {
        return getInt(intString, -1);
    }

    /**
     * 字符串转整型
     */
    public static int getInt(String intString, int defaultValue) {
        if (StringUtil.isNullOrEmpty(intString)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * 字符串转长整型,转换出错返回-1
     */
    public static long getLong(String longString) {
        return getLong(longString, -1L);
    }

    /**
     * 字符串转长整型
     */
    public static long getLong(String longString, long defaultValue) {
        if (StringUtil.isNullOrEmpty(longString)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(longString);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * 字符串转float类型, 转换出错返回-1.0
     */
    public static float getFloat(String floatString) {
        return getFloat(floatString, -1F);
    }

    /**
     * 字符串转float类型
     */
    public static float getFloat(String floatString, float defaultValue) {
        if (StringUtil.isNullOrEmpty(floatString)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(floatString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转double类型, 转换出错返回-1.0
     */
    public static double getDouble(String doubleString) {
        return getDouble(doubleString, -1);
    }

    /**
     * 字符串转double类型
     */
    public static double getDouble(String doubleString, float defaultValue) {
        if (StringUtil.isNullOrEmpty(doubleString)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(doubleString);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String formatNoGroup(Double db, int digits) {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumFractionDigits(digits);
            return nf.format(db);
        } catch (Exception e) {
        }
        return "";
    }

    public static String format(Double db, int digits) {
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(digits);
            return nf.format(db);
        } catch (Exception e) {
        }
        return "";
    }

    public static String format(String dbStr, int digits) {
        if (TextUtils.isEmpty(dbStr)) {
            return "";
        }
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(digits);
            Double db = Double.parseDouble(dbStr);
            return nf.format(db);
        } catch (Exception e) {
        }
        return "";
    }

    public static String formatZH(Double db) {
        try {
            if (db >= 0) {
                String unit = "";
                if (db < 1000) {
                    db = db;
                    unit = "";
                } else if (db < 10000) {
                    db = db / 1000.0;
                    unit = "千";
                } else {
                    db = db / 10000.0;
                    unit = "万";
                }
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                return nf.format(db) + unit;
            }
        } catch (Exception e) {
        }
        return "";
    }
}

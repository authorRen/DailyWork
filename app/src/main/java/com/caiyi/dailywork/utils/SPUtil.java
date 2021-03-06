package com.caiyi.dailywork.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.caiyi.dailywork.compant.DialyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SharedPreferences 工具类.<br/>
 * ps:1.多进程中使用SharedPreference概率性取值出错
 * 2.同一时刻使用apply()方式提交,高概率出现保存不到sp的问题.可以put多次，最后一次apply()提交即可.
 * 3.SharedPreferences不要保存太多的东西，耗内存.
 * Created by HuoGuangxu on 2016/10/19.
 */

public class SPUtil {
    public static final String SP_SEPARATOR = ";##;";

    private SPUtil() {
    }

    public static SharedPreferences getSP() {
        return DialyApplication.getmAppContext().getSharedPreferences(AppConfig.FUND_SP_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSP(String spName) {
        if (StringUtil.isNullOrEmpty(spName)) {
            throw new RuntimeException("sp name not null or empty");
        }
        return DialyApplication.getmAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static void putString(String key) {
        getSP().edit().putString(key, "").apply();
    }

    public static void putString(String key, String value) {
        getSP().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        return getSP().getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSP().edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defValue) {
        return getSP().getInt(key, defValue);
    }

    public static void putFloat(String key, float value) {
        getSP().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getFloat(key, -1.0f);
    }

    public static float getFloat(String key, float defValue) {
        return getSP().getFloat(key, defValue);
    }

    public static void putLong(String key, long value) {
        getSP().edit().putLong(key, value).apply();
    }

    public static long getLong(String key) {
        return getLong(key, -1);
    }

    public static long getLong(String key, long defValue) {
        return getSP().getLong(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSP().edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSP().getBoolean(key, defValue);
    }

    public static boolean contains(String key) {
        return getSP().contains(key);
    }

    public static void remove(String key) {
        getSP().edit().remove(key).apply();
    }

    /**
     * 清空sp
     */
    public static void clear() {
        getSP().edit().clear().apply();
    }

    /**
     * 保存List<String>到sp.以{@link #SP_SEPARATOR}分割,所以字符串中不要包含{@link #SP_SEPARATOR},否则保存的和读取的不一致。
     */
    public static void putStringList(String key, List<String> list) {
        if (ExtendUtil.isListNullOrEmpty(list)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (StringUtil.isNullOrEmpty(str)) {
                sb.append(" ");
            } else {
                sb.append(str);
            }
            sb.append(SP_SEPARATOR);
        }
        getSP().edit().putString(key, sb.toString()).apply();
    }

    /**
     * 获取指定key的List<String>
     */
    public static List<String> getStringList(String key) {
        String textList = getString(key);
        if (StringUtil.isNullOrEmpty(textList)) {
            return null;
        }
        String[] stringArray = textList.trim().split(SP_SEPARATOR);
        return Arrays.asList(stringArray);
    }

    /**
     * 保存List<Integer>到sp.以{@link #SP_SEPARATOR}分割,所以字符串中不要包含{@link #SP_SEPARATOR},否则保存的和读取的不一致。
     */
    public static void putIntList(String key, List<Integer> list) {
        if (ExtendUtil.isListNullOrEmpty(list)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int value : list) {
            sb.append(value).append(SP_SEPARATOR);
        }
        getSP().edit().putString(key, sb.toString()).apply();
    }

    /**
     * 获取指定key的List<Integer>
     */
    public static List<Integer> getIntList(String key) {
        String intList = getString(key);
        if (StringUtil.isNullOrEmpty(intList)) {
            return null;
        }
        List<Integer> values = new ArrayList<>();
        String[] intArray = intList.trim().split(SP_SEPARATOR);
        for (String value : intArray) {
            values.add(NumberUtil.getInt(value));
        }
        return values;
    }

    /***
     * 把List数据保存到SP
     */
    public static <T> void putListToSp(String key, List<T> list) {
        if (ExtendUtil.isListNullOrEmpty(list)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            sb.append(JsonUtil.encode(t)).append(SP_SEPARATOR);
        }
        getSP().edit().putString(key, sb.toString()).apply();
    }

    /***
     * 从SP中获取List类型的数据
     */
    public static <T> List<T> getListFromSp(String key, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        String text = SPUtil.getString(key);
        String[] classArray = text.trim().split(SP_SEPARATOR);
        for (String value : classArray) {
            T t = JsonUtil.decode(value, clazz);
            if (t == null) {
                continue;
            }
            list.add(t);
        }
        return list;
    }
}

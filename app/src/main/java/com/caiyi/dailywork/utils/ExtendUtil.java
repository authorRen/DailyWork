package com.caiyi.dailywork.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.caiyi.dailywork.compant.DialyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 辅助工具类
 * Created by HuoGuangxu on 2016/9/21.
 */

public class ExtendUtil {
    private void ExtendUtil() {

    }

    /**
     * 判断List集合是否为null/empty
     */
    public static boolean isListNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 对身份证输入限制.最长18位,[0, 9]和x, X.
     */
    public static void filterIDCardInput(EditText editText) {
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789xX"));
        }
    }

    /**
     * 对手机号输入限制.最长11位数字
     */
    public static void filterMobileNumberInput(EditText editText) {
        filterNumberLengthInput(editText, 11);
    }

    /**
     * EditText只能输入指定最大长度的数字
     */
    public static void filterNumberLengthInput(EditText editText, int length) {
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        }
    }

    /**
     * EditText只能输入指定最大长度的字符
     */
    public static void filterLengthInput(EditText editText, int length) {
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        }
    }

    /**
     * 验证是否合法手机号
     */
    public static boolean isValidMobileNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    /**
     * 检查sdcard 是否可用
     */
    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 判断是否是手机uri(tel:)
     */
    public static boolean isTelUri(String uri) {
        return !StringUtil.isNullOrEmpty(uri) && uri.toLowerCase().startsWith("tel:");
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        if (null == view) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//InputMethodManager.HIDE_NOT_ALWAYS等只能隐藏一次
        }
    }

    /**
     * 显示软键盘.<br/>
     * 在{@link android.app.Activity#onCreate(Bundle)}方法中无效,因为View还没创建.
     * 使用{@link View#postDelayed(Runnable, long)}方法延迟显示。
     */
    public static void showSoftInput(View view) {
        if (null == view) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 根据资源id获取资源文件名.
     */
    public static String getResourceName(int resId) {
        return DialyApplication.getmAppContext().getResources().getResourceEntryName(resId);
    }

    /**
     * SparseArray转List
     */
    public static <E> List<E> asList(SparseArray<E> sparseArray) {
        if (sparseArray == null) {
            return null;
        }
        List<E> arrayList = new ArrayList<>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }
        return arrayList;
    }
}

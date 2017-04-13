package com.caiyi.dailywork.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by HuoGuangXu on 2016/9/14.
 */

public class StringUtil {
    /**
     * 判断字符串是否为null或""
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * 判断可变字符串是否包含空(null或empty)字符串
     */
    public static boolean isAnyEmpty(String... strings) {
        if (strings == null || strings.length < 1) {
            return true;
        }
        for (String str : strings) {
            if (isNullOrEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串都不为null或""返回true,否则返回false.
     */
    public static boolean isNoneEmpty(final String... strings) {
        return !isAnyEmpty(strings);
    }

    /**
     * 判断数组是否为null或""
     */
    public static boolean isArrayEmpty(String[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 设置字符串中指定字符的样式
     *
     * @param context 上下文
     * @param text    字符串
     * @param keyword 关键字
     * @param colorId 指定字体的颜色.-1不设置颜色
     * @param sizeId  指定字体的大小.-1不设置字体大小
     * @return 特殊的字符串
     */
    public static SpannableStringBuilder buildSingleTextStyle(Context context, String text, String keyword, @ColorRes int colorId, @DimenRes int sizeId) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);

        Pattern p = Pattern.compile(keyword);
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (colorId != -1) {
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (sizeId != -1) {
                style.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return style;
    }

    /**
     * 设置字符串中指定字符的样式
     *
     * @param context 上下文
     * @param text    字符串
     * @param start   关键字开始位置
     * @param end     关键字结束位置
     * @param colorId 关键字的颜色.-1不设置颜色
     * @param sizeId  关键字的大小.-1不设置字体大小
     */
    public static SpannableStringBuilder buildSingleTextStyle(Context context, @NonNull String text, int start, int end, @ColorRes int colorId, @DimenRes int sizeId) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (colorId != -1) {
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (sizeId != -1) {
            style.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }

    /**
     * 设置字符串中指定多个字符的样式
     *
     * @param context       上下文
     * @param text          字符串
     * @param keywordArray  关键字数组。要一一对应。
     * @param colorResArray 颜色值资源数组。要一一对应。-1不设置颜色
     * @param sizeResArray  字符大小资源数组。要一一对应。-1不设置大小
     * @return 特殊的字符串
     */
    public static SpannableStringBuilder buildMulTextStyle(Context context, @NonNull String text, String[] keywordArray, @ColorRes int[] colorResArray, @DimenRes int[] sizeResArray) {
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(text);
        for (int i = 0; i < keywordArray.length; i++) {
            Pattern p = Pattern.compile(keywordArray[i]);
            Matcher matcher = p.matcher(text);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                if (colorResArray[i] != -1) {
                    spannableBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorResArray[i])), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (sizeResArray[i] != -1) {
                    spannableBuilder.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeResArray[i]), false), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        return spannableBuilder;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isNumber(String str) {
        if (StringUtil.isNullOrEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
package com.caiyi.dailywork.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 和流有关的工具类.
 * Created by HuoGuangxu on 2016/10/20.
 */

public class IOUtil {
    private IOUtil() {
        throw new UnsupportedOperationException("No instances!");
    }

    /**
     * 关闭流对象,忽略异常。
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //Ignored
            }
        }
    }

    /**
     * 关闭流对象,关闭失败抛出异常.
     */
    public static void closeCheckException(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }
}

package com.caiyi.dailywork.net;

import android.content.Context;
import android.util.Log;

import com.caiyi.dailywork.utils.Utilty;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by RZQ on 2017/5/16.
 */

public class OkHttpUtils {
    private static OkHttpClient mOkHttpClient;
    /** TAG */
    private static final String TAG = "OkHttpUtils";
    /** 分页的默认大小 */
    private static final int DEFAULT_PAGE_SIZE = 15;
    /** 默认超时时间 */
    private static final int DEFAULT_TIME_OUT = 30000;

    public OkHttpUtils() {
    }

    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 初始化Okhttp网络库
     *
     * @param context 全局上下文
     */
    public static void initOkHttpClient(final Context context) {
        getOkHttpClient().setConnectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
        getOkHttpClient().setReadTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);
        //保存本地时间与服务器时间差
        getOkHttpClient().networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long startTime = System.currentTimeMillis();
                Response response = chain.proceed(chain.request());
                try{
                    long nowTime = System.currentTimeMillis();
                    Date serverDate = new Date(response.header("Date"));
                    long serverTime = (nowTime - startTime) / 2 + serverDate.getTime();
                    Utilty.setServerTimeDelta(serverTime - nowTime);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                return response;
            }
        });
        //添加gzip支持
        getOkHttpClient().networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                    return chain.proceed(originalRequest);
                }
                Request compressedRequest = originalRequest.newBuilder().header("Content-Encoding", "gzip")
                        .method(originalRequest.method(), gzip(originalRequest.body())).build();
                return chain.proceed(compressedRequest);
            }

            private RequestBody gzip(final RequestBody body) {
                return new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return body.contentType();
                    }

                    @Override
                    public long contentLength() throws IOException {
                        return -1; //无法知道压缩后的数据大小
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                        body.writeTo(gzipSink);
                        gzipSink.close();
                    }
                };
            }
        });
        if (true) {
            getOkHttpClient().interceptors().add(new LogInterceptor());
        }
    }
}

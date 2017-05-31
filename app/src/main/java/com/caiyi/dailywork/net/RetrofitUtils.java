package com.caiyi.dailywork.net;

import com.caiyi.dailywork.inter.BlogService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by RZQ on 2017/5/25.
 */

public class RetrofitUtils {

    public void postRequest() {
        //构建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:1234/")
                .build();
        BlogService blogService = retrofit.create(BlogService.class);
        Call<ResponseBody> responseBodyCall = blogService.getBlog(2);
        /**
         * 用法类似于okhttp的Call
         * 不同的是如果是android系统回调方法执行在主线程
         */
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.print(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


}

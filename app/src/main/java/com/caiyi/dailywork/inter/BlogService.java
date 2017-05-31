package com.caiyi.dailywork.inter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * Created by RZQ on 2017/5/25.
 */

public interface BlogService {

    @GET("blog/{id}")
    Call<ResponseBody> getBlog(@Path("id") int id);

    /**
     * method 表示请求的方法，区分大小写，retrofit不会做处理
     * path 表示路径
     * hasBody 表示是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getData(@Path("id") int id);
}

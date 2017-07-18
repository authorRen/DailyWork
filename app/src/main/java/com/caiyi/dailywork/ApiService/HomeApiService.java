package com.caiyi.dailywork.ApiService;

import com.caiyi.dailywork.data.ADInfo;

import retrofit2.Callback;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Ren ZeQiang
 * @since 2017/7/12
 */
public interface HomeApiService {

    @POST("/user/....")
    public void getUser(@Query("userId") String userId, Callback<ADInfo> callback);

}

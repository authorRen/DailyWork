package com.caiyi.dailywork.inter;

/**
 * 网络请求接口
 *
 * @author Ren ZeQiang
 * @since 2017/7/4
 */
public interface HttpCallbackListener {
    //成功返回
    void onSuccess(String response);
    //异常返回
    void onError(Exception e);
}

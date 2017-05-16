package com.caiyi.dailywork.utils;

import android.support.annotation.IntDef;

/**
 * 全局常量
 * Created by HuoGuangxu on 2016/9/19.
 */

public class GlobalConstants {
    //标记从主页到城市搜索页面
    public static final int SEARCH_CITY_FROM_HOME = 0;
    //标记从社保查询页到城市搜索页面
    public static final int SEARCH_CITY_FROM_ORG = 1;

    public interface RESPONSE_CODE {
        /** 该城市暂不支持 */
        int CITY_UNSUPPORT = -5002;
        /** 更新公积金数据 */
        int UPDATE_FUND_INFO = -5004;
        /** 绑定公积金账号 */
        int BIND_FUND_ACCOUNT = -5006;
        /** 不满足贷款条件 */
        int LOAN_UNGRATIFIED_CONDITION = -5008;
        /** 错误为小赢返回错误 */
        int XIAO_YING_ERROR = -5010;
    }

    public interface HTTP_RESPONSE_CODE {
        /** 请求范围不符合要求 */
        int NON_CONFORMANCE = 416;
        /** 文件未修改 */
        int UNMODIFIED = 304;
        /** 部分内容 */
        int PARTIAL_CONTENT = 206;
    }

    /** 登录图片验证码 */
    public static final int LOGIN_IMAGE = 1;
    /** 登录短信验证码 */
    public static final int LOGIN_SMS = 2;
    /** 详单图片验证码 */
    public static final int DETAIL_IMAGE = 3;
    /** 详单短信验证码 */
    public static final int DETAIL_SMS = 4;

    @IntDef({LOGIN_IMAGE, LOGIN_SMS, DETAIL_IMAGE, DETAIL_SMS})
    public @interface PIN_TYPE {
    }

    public interface INTENT_PARAMS_KEY {
        String CITY_CODE = "CITY_CODE";
        String GJJ_LOC_NAME = "GJJ_LOC_NAME";
        String APP_DOWNLOAD_UPDATE = "APP_DOWNLOAD_UPDATE";
        String CITY_LIST = "CITY_LIST";
        String CITY_SELECT = "CITY_SELECT";
        String CITY_SEARCH_FROM = "CITY_SEARCH_FROM";
        String LOAN_PRODUCT = "LOAN_PRODUCT";
        String LOAN_APPLY_ID = "LOAN_APPLY_ID";
        String LOAN_BANK = "LOAN_BANK";
        String LOAN_ORDER = "LOAN_ORDER";
        String BANK_PROVINCE = "BANK_PROVINCE";
        String BANK_CITY = "BANK_CITY";
    }

    //在线客服使用标志.0:websocket;1:友盟
    public interface OnlineServiceType {
        int SERVICE_SOCKET = 0;
        int SERVICE_UMENG = 1;
    }

    //网络状态
    public interface NETWORK_TYPE {
        /** 未知网络 */
        int TYPE_UNKNOWN = -1;
        /** See:{@link android.net.ConnectivityManager#TYPE_WIFI wifi网络} */
        int TYPE_WIFI = 0;
        /** See:{@link android.net.ConnectivityManager#TYPE_MOBILE 手机网络} */
        int TYPE_MOBILE = 1;
    }

    //SharedPreference的key
    public interface SP_PARAMS_KEY {
        /** app更新是否下载完成 */
        String APK_DOWNLOAD_SUCCESS = "APK_DOWNLOAD_SUCCESS";
        /** app下载的版本号 */
        String APK_DOWNLOAD_VERSION = "APK_DOWNLOAD_VERSION";
        //是否显示过刷新账户向导 统计次数，第二次再弹出
        String REFRESH_ACCOUNT_GUIDE_COUNT = "REFRESH_ACCOUNT_GUIDE_COUNT";
        /** 社区搜索历史 */
        String FORUM_SEARCH_HISTORY = "FORUM_SEARCH_HISTORY";
        /** Splash版本 */
        String SPLASH_VERSION = "SPLASH_VERSION";
        /**
         * 每个手机号的登录形式
         * {@link com.caiyi.utils.LoginHelper.LoginState LoginState}
         */
        String LOGIN_STATE = "LOGIN_STATE";

        /** 登录框,记录每次输入的手机号 */
        String LOCAL_LOGIN_BOX_PHONE_KEY = "LOCAL_LOGIN_BOX_PHONE_KEY";

        /** 登录成功用户的手机号. */
        String LOCAL_USER_PHONE_KEY = "LOCAL_USER_PHONE_KEY";

        /** 保存测试域名 */
        String DOMAIN_FOR_DEBUG = "DOMAIN_FOR_DEBUG";
        
        String PRINT_REQUEST_HEADER = "PRINT_REQUEST_HEADER";
        
        String PRINT_RESPONSE_HEADER = "PRINT_RESPONSE_HEADER";
        
        String INTERCEPTOR_LOG_DISABLE = "INTERCEPTOR_LOG_DISABLE";
    }
    
    //网络缓存的key
    public interface CACHE_KEY{
        String FORUM_TAGS = "FORUM_TAGS";
    }

    public interface APP_UPDATE_TYPE {
        int NON_FORCE = 0;
        int FORCE = 1;
    }
}

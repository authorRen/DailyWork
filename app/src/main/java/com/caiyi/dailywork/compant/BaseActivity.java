package com.caiyi.dailywork.compant;

/**
 * @author dongqi
 * @since 2015/12/21.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.caiyi.dailywork.utils.StringUtil;

import java.util.LinkedList;

/**
 * 为了实现SDK Sersion 11 以下的FLAG_ACTIVITY_CLEAR_TASK。
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    /** DEBUG. */
    private static final boolean DEBUG = true;
    /** tag. */
    private static final String TAG = "BaseActivity";
    /** 后台切换回来的时间 */
    private static final long BACK_TIME = 60 * 1000;
    /** 下一次Activity启动时，新Activity的进入动画 */
    private static int nextEnterAnimWhenStarting;
    /** 下一次Activity启动时，旧Activity的退出动画 */
    private static int nextExitAnimWhenStarting;
    /** 下一次Activity结束时，新Activity的进入动画 */
    private static int nextEnterAnimWhenFinishing;
    /** 下一次Activity结束时，旧Activity的退出动画 */
    private static int nextExitAnimWhenFinishing;

    /** 启动时，新Activity的进入动画 */
    private int mEnterAnimWhenStarting;
    /** 启动时，旧Activity的退出动画 */
    private int mExitAnimWhenStarting;
    /** 结束时，新Activity的进入动画 */
    private int mEnterAnimWhenFinishing;
    /** 结束时，旧Activity的退出动画 */
    private int mExitAnimWhenFinishing;
    /** 判断此activity是否已经结束(onStop) */
    private boolean mIsStop = false;
    /**判断此activity是否已经结束(onDestroy)**/
    private boolean mIsDestroyed = false;
    /** 切换到后台的时间 */
    private long mBackGroundTime = 0;
    /** progressdialog.*/
    private Dialog mProgressDialog;
    /** local broadcast receiver manager.*/
    private LocalBroadcastManager mLbm;
    /** login receiver.*/
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onLoginSuccess();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mLiveActivityNum++;
        String name = getComponentName().getClassName();
        if (DEBUG) {
            Log.v(TAG, name);
        }
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
}

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsStop = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLiveActivityNum--;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsStop = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            getIntentData(getIntent());
        }
        mIsDestroyed = false;
        addToTask(this);
        // 设置本次Activity切换动画
        if (nextEnterAnimWhenStarting != 0 || nextExitAnimWhenStarting != 0) {
            mEnterAnimWhenStarting = nextEnterAnimWhenStarting;
            mExitAnimWhenStarting = nextExitAnimWhenStarting;
        }

        if (nextEnterAnimWhenFinishing != 0 || nextExitAnimWhenFinishing != 0) {
            mEnterAnimWhenFinishing = nextEnterAnimWhenFinishing;
            mExitAnimWhenFinishing = nextExitAnimWhenFinishing;
        }
        setNextPendingTransition(0, 0, 0, 0);
        mLbm = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        mIsDestroyed = true;
        removeFromTask(this);
        super.onDestroy();
        dismissDialog();
        mLbm.unregisterReceiver(mLoginReceiver);
    }

    protected void getIntentData(Intent intent) {
    }

    /** 子Activity可以重写此方法控制是否可以滑动关闭 */
    protected boolean onAttachSlider() {
        return true;
    }

    /**
     * 登陆成功
     */
    protected void onLoginSuccess() {
        //login success
        if (DEBUG) {
            Log.i(TAG, "onLoginSuccess");
        }
    }

    /**
     * 获取栈顶Activity
     *
     * @return Activity
     */
    public static Activity getTopActivity() {
        return mActivityStack.getLast();
    }

    /**
     * 将Activity加入栈管理队列中。
     *
     * @param activity
     *            Activity。
     */
    public static synchronized void addToTask(Activity activity) {
        // 移到顶端。
        mActivityStack.remove(activity);
        mActivityStack.add(activity);
    }

    /**
     * @return 当前有几个界面已打开
     */
    public static int getTaskActivities() {
        return mActivityStack.size();
    }

    /**
     * 包含activity
     *
     * @param name
     *            activity's name
     * @return 包含activity
     */
    public boolean hasActivity(String name) {
        return mActivityStack.toString().contains(name);
    }

    /**
     * 从栈管理队列中移除该Activity。
     *
     * @param activity
     *            Activity。
     */
    public static synchronized void removeFromTask(Activity activity) {
        mActivityStack.remove(activity);
        if (mActivityStack.isEmpty()) {
            if (sEmptyKillApp) {
                quitApp(activity.getApplicationContext());
            } else {
                sEmptyKillApp = true;
            }
        }
    }

    /**
     * 清除栈队列中的所有Activity。
     */
    public static synchronized void clearTask() {
        if (!mActivityStack.isEmpty()) {
            sEmptyKillApp = false;
        }
        for (Activity activity : mActivityStack) {
            activity.finish();
        }
    }

    /**
     * 清除栈队列中除exceptActivity以外的所有Activity。与FLAG_ACTIVITY_CLEAR_TASK功能类似。
     *
     * @param exceptActivity
     *            exceptActivity。
     */
    public static synchronized void clearTaskExcept(Activity exceptActivity) {
        for (Activity activity : mActivityStack) {
            if (activity != exceptActivity) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用。
     *
     * @param context
     *            Context.
     */
    public static void quitApp(Context context) {
        // if (isAppInForeground()) {
        // AlarmManager alarmManager =
        // (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Intent intent = new Intent(BaseActivity.ACTION_ALARM_DING_CALLBACK);
        // PendingIntent mPintent =
        // PendingIntent.getBroadcast(context.getApplicationContext(), 0,
        // intent, 0);
        // final int delayTime = 2000;
        // //用AlarmManager延迟2秒发送通知，以免进程过早被杀死导致广播无法收到。
        // alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() +
        // delayTime, mPintent);
        // }

        // android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 判断应用是否在前台。
     *
     * @return 如果在前台，返回true.
     */
    public static boolean isAppInForeground() {
        return mLiveActivityNum > 0;
    }

    /**
     * 判断应用程序是否刚刚回到前台。
     *
     * @return 如果是，返回true。
     */
    public static boolean isFirstIn() {
        return sIsFirstIn;
    }

    /**
     * 设置状态，标识应用程序是否刚刚回到前台。
     *
     * @param isFirstIn
     *            应用程序是否刚刚回到前台。
     */
    public static void setFirstIn(boolean isFirstIn) {
        sIsFirstIn = isFirstIn;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_MENU && event.isLongPress() || super.onKeyDown(keyCode, event);
    }

    /**
     * 设置下一次Activity的切换动画，enter与exit同时为0时用默认
     *
     * @param enterAnimWhenStarting
     *            下一次Activity启动时，新Activity的进入动画
     * @param exitAnimWhenStarting
     *            下一次Activity启动时，旧Activity的退出动画
     * @param enterAnimWhenFinishing
     *            下一次Activity结束时，新Activity的进入动画
     * @param exitAnimWhenFinishing
     *            下一次Activity结束时，旧Activity的退出动画
     * @see Activity#overridePendingTransition(int, int)
     */
    public static void setNextPendingTransition(int enterAnimWhenStarting, int exitAnimWhenStarting,
                                                int enterAnimWhenFinishing, int exitAnimWhenFinishing) {
        BaseActivity.nextEnterAnimWhenStarting = enterAnimWhenStarting;
        BaseActivity.nextExitAnimWhenStarting = exitAnimWhenStarting;
        BaseActivity.nextEnterAnimWhenFinishing = enterAnimWhenFinishing;
        BaseActivity.nextExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

    /**
     * 设置本次的切换动画，enter与exit同时为0时用默认
     *
     * @param enterAnimWhenStarting
     *            启动时，新Activity的进入动画
     * @param exitAnimWhenStarting
     *            启动时，旧Activity的退出动画
     * @param enterAnimWhenFinishing
     *            结束时，新Activity的进入动画
     * @param exitAnimWhenFinishing
     *            结束时，旧Activity的退出动画
     * @see Activity#overridePendingTransition(int, int)
     */
    protected void setPendingTransition(int enterAnimWhenStarting, int exitAnimWhenStarting,
                                        int enterAnimWhenFinishing, int exitAnimWhenFinishing) {
        // mEnterAnimWhenStarting = enterAnimWhenStarting;
        // mExitAnimWhenStarting = exitAnimWhenStarting;
        // mEnterAnimWhenFinishing = enterAnimWhenFinishing;
        // mExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

    /**
     * 设置切换动画，enter与exit同时为0时用默认
     *
     * @param enterAnimWhenStarting
     *            启动时，新Activity的进入动画
     * @param exitAnimWhenStarting
     *            启动时，旧Activity的退出动画
     * @param enterAnimWhenFinishing
     *            结束时，新Activity的进入动画
     * @param exitAnimWhenFinishing
     *            结束时，旧Activity的退出动画
     */
    protected void setNewPendingTransition(int enterAnimWhenStarting, int exitAnimWhenStarting,
                                           int enterAnimWhenFinishing, int exitAnimWhenFinishing) {
        mEnterAnimWhenStarting = enterAnimWhenStarting;
        mExitAnimWhenStarting = exitAnimWhenStarting;
        mEnterAnimWhenFinishing = enterAnimWhenFinishing;
        mExitAnimWhenFinishing = exitAnimWhenFinishing;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // 添加Activity启动的动画
        if (mEnterAnimWhenStarting != 0 || mExitAnimWhenStarting != 0) {
            overridePendingTransition(mEnterAnimWhenStarting, mExitAnimWhenStarting);
            mEnterAnimWhenStarting = 0;
            mExitAnimWhenStarting = 0;
        }
    }

    @Override
    public void finish() {
        super.finish();
        // 添加Activity退出的动画
        if (mEnterAnimWhenFinishing != 0 || mExitAnimWhenFinishing != 0) {
            overridePendingTransition(mEnterAnimWhenFinishing, mExitAnimWhenFinishing);
            mEnterAnimWhenFinishing = 0;
            mExitAnimWhenFinishing = 0;
        }
    }

    /**
     * 退出app
     */
    protected void exit() {
        // if (DEBUG) {
        for (int i = 0; i < mActivityStack.size(); i++) {
            if (DEBUG) {
                Log.v(TAG, "finish activity:" + mActivityStack.get(i).getClass().getName());
            }
            mActivityStack.get(i).finish();
        }
        // }
    }

    /**
     * 不能直接继承baseactivity的activity，在onresume是调用
     */
    public static void addLiveActivityNum() {
        mLiveActivityNum++;
    }

    /**
     * 不能直接继承baseactivity的activity，onpause时调用
     */
    public static void decLiveActivityNum() {
        mLiveActivityNum--;
    }

    /**
     * 窗口栈。
     */
    private static LinkedList<Activity> mActivityStack = new LinkedList<Activity>();

    /** 当前活动的Activity数，用来判断该应用是否在前台运行。 */
    private static int mLiveActivityNum = 0;

    /** 是否刚刚回到前台。 */
    private static boolean sIsFirstIn;

    /** 当没有Activity时，是否杀死进程。 */
    private static boolean sEmptyKillApp = true;

    /**
     * @return 判断当前activity是否在运行 (onStop)
     */
    public boolean isStop() {
        return mIsStop;
    }

    /**
     * @return 判断当前activity是否在运行 (onDestroy)
     **/
    public boolean isDestroyed() {
        return mIsDestroyed || isFinishing();
    }

    /**
     * 默认采用short toast
     *
     * @param toast
     *            str to toast
     */
    protected void showToast(String toast) {
        if (!StringUtil.isNullOrEmpty(toast)) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(String toast, String defaultToast) {
        if (!StringUtil.isNullOrEmpty(toast)) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isNullOrEmpty(defaultToast)) {
            Toast.makeText(this, defaultToast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 默认采用short toast
     *
     * @param toast
     *            str to toast
     */
    protected void showToast(@StringRes int toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String toast, @StringRes int resId) {
        if (!StringUtil.isNullOrEmpty(toast)) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /** show dialog.*/
    protected  void showDialog() {
        mProgressDialog.show();
    }

    /**
     * 设置对话框是否可以取消
     * @param cancelable 是否可以取消
     */
    protected void setProgressDialogCancelable(boolean cancelable) {
        if (null != mProgressDialog) {
            mProgressDialog.setCancelable(false);
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 检测网络状态
     * @return is networkconneted
     */
    protected boolean isNetConneted() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    //没有findViewById的View使用
    protected void setViewClickListeners(int... viewIds) {
        for (int id : viewIds) {
            if (id != 0) {
                View view = findViewById(id);
                if (view != null){
                    view.setOnClickListener(this);
                }
            }
        }
    }

    //已经findViewById,直接设置
    protected void setViewClickListeners(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    protected void openActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void goBackLastActivityWithExtra(Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 从上一个Activity获取Intent中String类型的数据
     *
     * @param defaultValue 指定的key为null,返回defaultValue
     */
    protected String getStringExtra(String key, @NonNull String defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        String value = intent.getStringExtra(key);
        return value == null ? defaultValue : value;
    }

    /**
     * 从上一个Activity的Intent中获取T类型的数据
     *
     * @param defaultValue 指定的key为null,返回defaultValue
     */
    @SuppressWarnings("unchecked")
    protected <T> T getSerializableExtra(String key, T defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        T value = (T) intent.getSerializableExtra(key);
        return value == null ? defaultValue : value;
    }

    protected <T> T getSerializableExtra(String key) {
        return getSerializableExtra(key, null);
    }
}
package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by RZQ on 2017/6/1.
 */

public class Base2Activity extends AppCompatActivity {

    private static final String TAG = "Base2Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //杀掉当前进程的代码，不能杀掉其他的进程
        // android.os.Process.killProcess(android.os.Process.myPid());
    }
}

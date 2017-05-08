package com.caiyi.dailywork.compant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by RZQ on 2017/4/28.
 */

public class MyReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Log.i("TAG", "onReceive ---" + data);
    }
}

package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/6/5.
 */

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        TextView succee = (TextView) findViewById(R.id.set_success);
        succee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });
    }

}

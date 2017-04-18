package com.caiyi.dailywork.compant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caiyi.dailywork.R;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvResolution;
    private Button btnPress;
    /** 获取手机屏幕分辨率相关类*/
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        setViewClickListeners(R.id.btn_span);

    }

    private void initView() {
        tvResolution = (TextView) findViewById(R.id.tv_resolution);
        btnPress = (Button) findViewById(R.id.btn_press);
        btnPress.setOnClickListener(this);

        findViewById(R.id.btn_floating).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_press:
                getResolution();
                break;
            case R.id.btn_floating:
                openActivity(FloatingActivity.class);
                break;
            case R.id.btn_span:
                openActivity(SpannedActivity.class);
                break;
            default:
                break;
        }

    }

    /**
     * 获取手机屏幕的分辨率
     */
    private void getResolution() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //获取手机屏幕的宽度和高度像素单位为px
        String str = getResources().getString(R.string.resolution) + dm.widthPixels  + "*" + dm.heightPixels;
        tvResolution.setText(str);
    }
}

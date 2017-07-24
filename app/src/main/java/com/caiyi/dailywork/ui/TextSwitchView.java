package com.caiyi.dailywork.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.caiyi.dailywork.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ren ZeQiang
 * @since 2017/7/18
 */
public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private int mIndex = -1;
    private Context mContext;
    private Timer mTimer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mIndex = next(); //取得下标志
                    updateText(); //更新TextSwitcher显示内容
                    break;
            }
        }
    };

    private String[] resources = {"窗前明月光", "疑是地上霜", "举头望明月", "低头思故乡"};

    private float textSize;

    private int maxLine;

    private int gravity;

    private int textColor;

    private int mIconPadding;



    public TextSwitchView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        this.setFactory(this);
        /*自定义动画*/
        this.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.in_animation));
        this.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.out_animation));

        /*自定义样式*/
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextSwitchView);
        mIconPadding = (int) ta.getDimension(R.styleable.TextSwitchView_tsv_padding, 0);
        textColor = ta.getColor(R.styleable.TextSwitchView_tsv_textColor, 0xff999999);
        textSize = ta.getDimension(R.styleable.TextSwitchView_tsv_textSize, 10);
        maxLine = ta.getInteger(R.styleable.TextSwitchView_tsv_maxLine, 1);
        gravity = ta.getInteger(R.styleable.TextSwitchView_tsv_textGravity, Gravity.LEFT);
        ta.recycle();
    }

    public void setResources(String[] res) {
        this.resources = res;
    }

    public void setTextStillTime(long time) {
        if (mTimer == null) {
            mTimer = new Timer();
        } else {
            mTimer.scheduleAtFixedRate(new MyTask(), 1, time); //每三秒更新
        }
    }

    private class MyTask extends TimerTask{

        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    }

    private int next() {
        int flag = mIndex + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        this.setText(resources[mIndex]);
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(mContext);
//        tv.setTextColor(textColor);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        tv.setMaxLines(maxLine);
//        tv.setGravity(Gravity.CENTER_VERTICAL | gravity);
//        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return tv;
    }
}

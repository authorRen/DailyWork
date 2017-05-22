package com.caiyi.dailywork.data;

import android.animation.ValueAnimator;
import android.widget.TextView;

import com.caiyi.dailywork.inter.IRiseNumber;

import java.text.DecimalFormat;

/**
 * Created by RZQ on 2017/5/18.
 */

public class RiseNumber implements IRiseNumber{

    private static final int STOPPED = 0;

    private static final int RUNNING = 1;

    private int mPlayingState = STOPPED;
    /** 执行动画的对象 */
    private TextView textView;

    private long duration = 5000; //默认是5秒
    /** 起始数字 */
    private float from;
    /** 结束数字 */
    private float to;

    private DecimalFormat fnum;

    private EndListener mEndListener = null;
    /**
     * 1.int 2.float
     */
    private int numberType = 2;

    public RiseNumber(TextView textView, long duration, float from, float to) {
        this.textView = textView;
        this.duration = duration;
        this.from = from;
        this.to = to;
    }

    /**
     * 判断动画是否正在播放
     *
     * @return
     */
    public boolean isRunning() {
        return (mPlayingState == RUNNING);
    }

    /**
     * 跑小数动画
     */
    public void runFloat() {
        fnum = new DecimalFormat("##0.00");
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textView.setText(fnum.format(Float.parseFloat(valueAnimator
                        .getAnimatedValue().toString())));
                if (valueAnimator.getAnimatedFraction() >= 1) {
                    mPlayingState = STOPPED;
                    if (mEndListener != null) {
                        //通知监听器，动画结束事件
                        mEndListener.onEndFinish();
                    }

                }
            }
        });
        animator.start();
    }

    /**
     * 跑整数动画
     */
    public void runInt() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt((int) from,
                (int) to);
        valueAnimator.setDuration(duration);

        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //设置瞬时的数据值到界面上
                        textView.setText(valueAnimator.getAnimatedValue().toString());
                        if (valueAnimator.getAnimatedFraction() >= 1) {
                            //设置状态为停止
                            mPlayingState = STOPPED;
                            if (mEndListener != null)
                                //通知监听器，动画结束事件
                                mEndListener.onEndFinish();
                        }
                    }
                });
        valueAnimator.start();
    }

    @Override
    public void start() {
        if (!isRunning()) {
            mPlayingState = RUNNING;
            if (numberType == 1)
                runInt();
            else
                runFloat();
        }
    }

    @Override
    public void setOnEndListener(RiseNumber.EndListener listener) {
            mEndListener = listener;
    }

    public interface EndListener {
        /**
         * 当动画播放结束时回调方法
         */
         void onEndFinish();
    }

}

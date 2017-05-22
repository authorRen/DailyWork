package com.caiyi.dailywork.inter;

import com.caiyi.dailywork.data.RiseNumber;

/**
 * 增长数字的接口
 *
 * Created by RZQ on 2017/5/18.
 */

public interface IRiseNumber {
    /**
     * 开始播放动画的方法
     */
    public void start();

    /**
     * 设置小数
     *
     * @param number
     */
//    public void withNumber(float number);

    /**
     * 设置整数
     *
     * @param number
     */
//    public void withNumber(int number);

    /**
     * 设置动画播放时长
     */
//    public void setDuration(long duration);

    /**
     * 设置动画结束监听器
     */
     void setOnEndListener(RiseNumber.EndListener listener);
}

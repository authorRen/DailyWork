package com.caiyi.dailywork.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 封装EditText
 *          -1 普通类型  0 自带清除给功能  1 自带密码查看功能
 *
 * Created by RZQ on 2017/4/20.
 */

public class PowerfulEditText extends android.support.v7.widget.AppCompatEditText {

    /*普通类型*/
    private static final int TYPE_NORMAL = -1;

    /*自带清除功能的类型*/
    private static final int TYPE_CAN_CLEAR = 0;

    /*自带密码查看功能的类型*/
    private static final int TYPE_CAN_WATCH_PWD = 1;

    public PowerfulEditText(Context context) {
        this(context, null);
    }

    public PowerfulEditText(Context context, AttributeSet attrs) {
        //这里构造方法很重要，不加这个很多属性不能在XML中定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PowerfulEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 实现自己对EditText处理的逻辑
     */
    private void init() {
        //获取EditText的DrawableRight，假如没有设置我们就使用默认的图片，左上右下

    }
}

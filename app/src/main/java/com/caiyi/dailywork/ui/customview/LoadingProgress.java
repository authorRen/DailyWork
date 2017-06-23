package com.caiyi.dailywork.ui.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.LoadingDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by RZQ on 2017/6/22.
 */

public class LoadingProgress extends View {

    public LoadingProgress(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private Animatable mDrawable;

    @Override
    public void setVisibility(int visibility) {
        if (null != mDrawable) {
            if (visibility == VISIBLE) {
                mDrawable.start();
            } else {
                mDrawable.stop();
            }
        }
        super.setVisibility(visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == VISIBLE) {
            if (mDrawable != null) {
                mDrawable.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDrawable != null) {
            mDrawable.stop();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        setBackgroundDrawable(new LoadingDrawable(context, this));
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        if (mDrawable == background) {
            return;
        }
        if (background != null) {
            if (mDrawable != null) {
                mDrawable.stop();
            }
            if (background instanceof Animatable) {
                mDrawable = (Animatable) background;
                mDrawable.start();
            } else {
                mDrawable = null;
            }
        }
        super.setBackgroundDrawable(background);
    }
}

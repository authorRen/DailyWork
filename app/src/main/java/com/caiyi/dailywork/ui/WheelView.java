package com.caiyi.dailywork.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;


import com.caiyi.dailywork.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 循环滚动内容View
 *
 * 若内容长度不够一屏，则不会循环滚动！
 *
 * @author CJL
 * @since 2016-02-24
 */
public class WheelView extends View implements GestureDetector.OnGestureListener, NestedScrollingChild {
    /** 数据 */
    private List<String> mData = new ArrayList<>();
    /** 调整位置持续时间 */
    private static final int FOCUS_DURATION = 100;

    private int mNormalColor;
    private int mSelectedColor;
    private int mSelOffset;
    private int mTextSize;
    private int mItemHeight;
    private boolean mAllowCycle;

    private Rect mRect = new Rect();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /** 手势 **/
    private GestureDetectorCompat mGesture;
    /** 滚动计算器 **/
    private ScrollerCompat mScroller;

    /** 总高度是否足够循环滚动 **/
    private boolean mCanCycle;
    private int mScrollY;
    private boolean mIsPressing = false;

    /** 渐变效果 **/
    private Shader mShader;
    private NestedScrollingChildHelper nestScrollHelper;

    public interface OnDateSelectListener {
        void onDateSelect();
    }

    OnDateSelectListener mListener;

    public void setOnDateSelectListener(OnDateSelectListener listener) {
        this.mListener = listener;
    }

    public WheelView(Context context) {
        super(context);
        init(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public WheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WheelView, 0, 0);

        mAllowCycle = a.getBoolean(R.styleable.WheelView_wvAllowCycle, true);
        mNormalColor = a.getColor(R.styleable.WheelView_wvNormalColor,
                ContextCompat.getColor(context, R.color.gjj_black));
        mSelectedColor = a.getColor(R.styleable.WheelView_wvActiveColor,
                ContextCompat.getColor(context, R.color.gjj_text_blue));

        mTextSize = a.getDimensionPixelSize(R.styleable.WheelView_wvTextSize, 30);
        mItemHeight = a.getDimensionPixelSize(R.styleable.WheelView_wvItemHeight, 60);
        mSelOffset = a.getDimensionPixelOffset(R.styleable.WheelView_wvSelOffset,
                (int) (context.getResources().getDisplayMetrics().density + 0.5f));

        CharSequence[] data = a.getTextArray(R.styleable.WheelView_wvData);
        if (data != null) {
            for (CharSequence cs : data) {
                mData.add((String) cs);
            }
        } else {
            for (int i = 0; i <= 12; i++) {
                mData.add(String.valueOf(i));
            }
        }

        mPaint.setTextAlign(Paint.Align.CENTER);
        mGesture = new GestureDetectorCompat(context, this);
        mScroller = ScrollerCompat.create(context);
        nestScrollHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }


    /**
     * 更新数据
     *
     * @param data
     *            数据
     */
    public void updateData(List<String> data) {
        mData.clear();
        mData.addAll(data);
        mScroller.abortAnimation();
        // mScrollY = 0;

        checkSize();
        invalidate();
    }

    /**
     * 获取当前选中的位置
     *
     * @return 位置 数据位置，从0开始
     */
    public int getCurrentPos() {
        final int sy = -mScrollY % (mItemHeight * mData.size());// 当前滚动距离
        final int cf = sy / mItemHeight;// 当前焦点item位置
        final int cfOffset = sy % mItemHeight; // 当前焦点item偏移

        final int halfH = mItemHeight >> 1;
        if (cfOffset >= -halfH || cfOffset <= halfH) {
            return getTextRealPos(cf);
        } else if (cfOffset > halfH) {
            return getTextRealPos(cf + 1);
        } else {
            return getTextRealPos(cf - 1);
        }
    }

    /**
     * 获取当前选中的位置文字
     *
     * @return 文字
     */
    public String getCurrentText() {
        return mData.get(getCurrentPos());
    }

    public void setCurrentPos(int pos) {
        setCurrentPos(pos, true);
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public List<String> getWhellData() {
        return Collections.unmodifiableList(mData);
    }

    /**
     * 设置当前显示的项
     *
     * @param pos
     *            选中的项
     * @param smoothScroll
     *            true平滑滚动，false 无动画
     */
    public void setCurrentPos(int pos, boolean smoothScroll) {
        pos = pos % mData.size();
        final int sy = -mScrollY % (mItemHeight * mData.size());// 当前滚动距离
        final int cf = getTextRealPos(sy / mItemHeight);// 当前焦点item位置

        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        if (smoothScroll) {
            final int size = mData.size();
            final int halfSize = size >> 1;
            if (cf - pos > halfSize) { // 保证循环滚动界点出能直接就近滚动过去，而不是只能向下滚动
                mScroller.startScroll(0, mScrollY, 0, mItemHeight * (cf - pos - size));
            } else if (pos - cf > halfSize) {
                mScroller.startScroll(0, mScrollY, 0, mItemHeight * (-pos + cf + size));
            } else {
                mScroller.startScroll(0, mScrollY, 0, mItemHeight * (cf - pos));
            }
        } else {
            mScrollY += mItemHeight * (cf - pos);
            notifyScroll();
        }
        invalidate();
    }

    /**
     *
     * @return 当前是否允许循环滚动
     */
    public boolean isAllowCycle() {
        return mAllowCycle;
    }

    /**
     *
     * @param allowCycle
     *            设置是否可以循环滚动（要能循环滚动还需要内容高度大于View高度）
     */
    public void setAllowCycle(boolean allowCycle) {
        this.mAllowCycle = allowCycle;
        checkSize();
        postInvalidate();
    }

    /**
     *
     * @return 当前是否可以循环滚动，需要 setAllowCycle 为true 并且内容高度大于View高度
     */
    public boolean canCycle() {
        return mCanCycle;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateShader(w, h);
        super.onSizeChanged(w, h, oldw, oldh);
        checkSize();
    }

    private void updateShader(int w, int h) {
        int zeroColor = Color.argb(0, Color.red(mNormalColor), Color.green(mNormalColor), Color.blue(mNormalColor));
        final float p1 = (h / 2f - mItemHeight) / h;
        final float p2 = (h / 2f + mItemHeight) / h;
        mShader = new LinearGradient(0, 0, 0, h, new int[] { zeroColor, mNormalColor, mNormalColor, zeroColor },
                new float[] { 0, p1, p2, 1 }, Shader.TileMode.CLAMP);
    }

    /**
     * 检查总高度是否可以循环滚动！
     */
    private void checkSize() {
        mCanCycle = mAllowCycle && mItemHeight * mData.size() > getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mData.size() == 0) {
            return;
        }

        final int h = getHeight();
        final int left = getPaddingLeft();
        final int right = getWidth() - getPaddingRight();
        final int totalH = mItemHeight * mData.size();
        final int fct = (h - mItemHeight) >> 1;  //当前显示条目的上y

        final int sy = -mScrollY % totalH;// 当前滚动距离
        final int cf = sy / mItemHeight;// 当前焦点item
        final int cfOffset = sy % mItemHeight; // 当前焦点item偏移

        // 画中间焦点文字
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mSelectedColor);
        canvas.save();
        canvas.clipRect(left, fct, right, fct + mItemHeight);  //中间item区域块
        mRect.set(left, fct - cfOffset, right, fct - cfOffset + mItemHeight);
        drawText2Rect(getTextByPos(cf), canvas, mRect, mPaint, mSelOffset);
        if (cfOffset < 0) { // 画上一个item
            mRect.set(mRect.left, mRect.top - mItemHeight, mRect.right, mRect.top);
            drawText2Rect(getTextByPos(cf - 1), canvas, mRect, mPaint, mSelOffset);
        } else if (cfOffset > 0) { // 画下一个item
            mRect.set(mRect.left, mRect.bottom, mRect.right, mRect.bottom + mItemHeight);
            drawText2Rect(getTextByPos(cf + 1), canvas, mRect, mPaint, mSelOffset);
        }
        canvas.restore();

        canvas.save();
        canvas.clipRect(left, 0, right, fct); //当前item上方区域

        final int tMaxCount = (h - mItemHeight) / (mItemHeight * 2) + 1; // 上半部分最多显示item数量
        final int ty = fct - mItemHeight * tMaxCount - cfOffset;

        boolean hasClipBottom = false;

        mPaint.setShader(mShader);
        mPaint.setAlpha(255);
        for (int i = 0, pos = cf - tMaxCount; pos < cf + tMaxCount + 1; pos++, i++) {
            if (!mCanCycle && (pos < 0 || pos >= mData.size())) { // 可以循环滚动
                continue;
            }

            int t = ty + i * mItemHeight;
            mRect.set(left, t, right, t + mItemHeight);
            drawText2Rect(getTextByPos(pos), canvas, mRect, mPaint, 0);

            if (!hasClipBottom && t + mItemHeight > fct) {
                canvas.clipRect(left, fct + mItemHeight, right, h, Region.Op.REPLACE);
                hasClipBottom = true;
            }
        }
        mPaint.setShader(null);
        canvas.restore();
    }

    /**
     * 获取指定位置的文字（位置可循环取，范围无限大）
     *
     * @param pos
     *            位置
     * @return 文字
     */
    private String getTextByPos(int pos) {
        return mData.get(getTextRealPos(pos));
    }

    /**
     * 获取指定位置的文字（位置可循环取，范围无限大）
     *
     * @param pos
     *            位置
     * @return 实际位置
     */
    private int getTextRealPos(int pos) {
        int idx = pos % mData.size();
        return idx >= 0 ? idx : mData.size() + idx;
    }

    /**
     * 在矩形中画文字
     *
     * @param text
     *            文字
     * @param c
     *            画布
     * @param r
     *            文字在该矩形正中心
     * @param p
     *            画笔
     */
    protected void drawText2Rect(String text, Canvas c, Rect r, Paint p, int offset) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Paint.FontMetrics fm = p.getFontMetrics();
        float x = r.centerX() + offset;
        float y = r.top + (r.bottom - r.top - fm.bottom + fm.top) / 2 - fm.top;
        // c.save();
        // c.clipRect(r);
        c.drawText(text, x, y, p);
        // c.restore();
    }

    /**
     * 检查当前位置，让当前焦点项居中
     */
    private void checkAndScrollToFocusPos() {
        if (mIsPressing) {
            return;
        }
        final int sy = -mScrollY % (mItemHeight * mData.size());// 当前滚动距离
        final int cfOffset = sy % mItemHeight; // 当前焦点item偏移
        if (cfOffset == 0) {
            notifyScroll();
            return;
        }
        final int halfH = mItemHeight / 2;
        int deltaY;

        if (cfOffset > 0 && cfOffset <= halfH) {
            deltaY = cfOffset;
        } else if (cfOffset > halfH) {
            deltaY = -mItemHeight + cfOffset;
        } else if (cfOffset < 0 && cfOffset >= -halfH) {
            deltaY = cfOffset;
        } else {
            deltaY = mItemHeight + cfOffset;
        }

        mScroller.startScroll(0, mScrollY, 0, deltaY, FOCUS_DURATION);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mScrollY = mScroller.getCurrY();
            invalidate();
        } else {
            checkAndScrollToFocusPos();
        }
        super.computeScroll();
    }

    private int mCurrentPos = -1;

    void notifyScroll() {
        if (!mScroller.isFinished() || mItemHeight <= 0) {
            return;
        }
        final int pos = -mScrollY / mItemHeight;

        if (mCurrentPos != pos) {
            mCurrentPos = pos;
            notifyChange();
        }
    }

    private void notifyChange() {
        if (mListener == null) {
            return;
        }
        mListener.onDateSelect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onUp();
        }
        return mGesture.onTouchEvent(event);
    }

    public void onUp() {
        mIsPressing = false;
        if (mScroller.isFinished()) {
            checkAndScrollToFocusPos();
        }
        stopNestedScroll();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mIsPressing = true;
        mScroller.abortAnimation();

        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }

        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float y = e.getY();
        final int sy = -mScrollY % (mItemHeight * mData.size());// 当前滚动距离
        final int cf = sy / mItemHeight; // 当前焦点item
        final int cfOffset = sy % mItemHeight; // 当前焦点item偏移

        final int ch = getHeight() >> 1;
        int deltaPos = (int) Math.ceil((y - ch - (mItemHeight >> 1) - cfOffset) / mItemHeight);
        int pos = (cf + deltaPos + mData.size()) % mData.size();
        setCurrentPos(pos, true);
        return true;
    }

    int[] consumed = new int[2];
    int[] offsetInWindow = new int[2];

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int dx = (int) distanceX;
        int dy = (int) distanceY;

        if (!mCanCycle) { // 不可循环的需要判断边界
            consumed[0] = 0;

            int sy = mScrollY;
            sy -= distanceY;
            final int minY = -mItemHeight * (mData.size() - 1);
            if (sy < minY) {
                consumed[1] = minY - mScrollY;
                mScrollY = minY;
            } else if (sy > 0) {
                consumed[1] = -mScrollY;
                mScrollY = 0;
            } else {
                consumed[1] = dy;
                mScrollY -= dy;
            }
        } else {
            mScrollY -= (int) distanceY;
            consumed[0] = 0;
            consumed[1] = dy;
        }

        if (dispatchNestedScroll(consumed[0], consumed[1], dx - consumed[0], dy - consumed[1], offsetInWindow)) {
            e2.offsetLocation(offsetInWindow[0], offsetInWindow[1]);
        }
        postInvalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int minY = Integer.MIN_VALUE;
        int maxY = Integer.MAX_VALUE;
        if (!mCanCycle) {
            minY = -mItemHeight * (mData.size() - 1);
            maxY = 0;
        }
        mScroller.fling(0, mScrollY, (int) velocityX, (int) velocityY, 0, 0, minY, maxY);
        invalidate();
        return true;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nestScrollHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestScrollHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return nestScrollHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        nestScrollHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestScrollHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
            int[] offsetInWindow) {
        return nestScrollHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return nestScrollHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return nestScrollHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return nestScrollHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}

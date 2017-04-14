package com.caiyi.dailywork.ui.customview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/4/14.
 */

public class FormInputView extends RelativeLayout{

    private Context mContext;
    private View mRootView;
    private ImageView mIconView;
    private EditText mInputView;
    private ImageView mClearView;
    private String mKeyWord;

    public FormInputView(Context context) {
        this(context, null);
    }

    public FormInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        mRootView = View.inflate(mContext, R.layout.input_form, this);
        mIconView = ((ImageView) findViewById(R.id.iv_icon));
        mInputView = ((EditText) findViewById(R.id.input_edittxt));
        mClearView = ((ImageView) findViewById(R.id.iv_clear_text));
    }

    /** 获取EditText的文本内容 */
    public String getContent() {
        if (mInputView != null) {
            return mInputView.getText().toString().trim();
        }
        return "";
    }

    /** 设置ImageView的图片*/
    public FormInputView setDrawable(@DrawableRes int DrawableId) {
        if (mIconView != null) {
            mIconView.setBackgroundResource(DrawableId);
        }
        return this;
    }

    /** 设置EditText的Hint值*/
    public FormInputView setHint(String hint) {
        if (hint == null) {
            return this;
        }
        if (mInputView != null) {
            mInputView.setHint(hint);
        }
        return this;
    }

    /** 设置清除按钮是否显示以及刷新按钮 */
    public FormInputView isClearView() {
        mInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mKeyWord = mInputView.getText().toString().trim();
                if (mKeyWord.length() > 0) {
                    mClearView.setVisibility(VISIBLE);
                } else {
                    mClearView.setVisibility(INVISIBLE);
                }
            }
        });
       return this;
    }

    /** 点击清除view删除内容 */
    public FormInputView delete() {
        mClearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputView.getText().clear();
            }
        });
        return this;
    }


}

package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.ui.customview.FormInputView;


public class FloatingActivity extends BaseActivity implements View.OnClickListener{

    private EditText mInputText;

    private String mKeyword;

    /**
     * 清除按钮
     */
    private ImageView mClearText;

    private EditText mNameEdit;

    private EditText mCardEdit;

    private TextView mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating);
        initView();
    }

    private void initView() {
        findViewById(R.id.floatingActionButton).setOnClickListener(this);

        mClearText = (ImageView) findViewById(R.id.iv_clear_text);
        mClearText.setOnClickListener(this);

        mInputText = (EditText) findViewById(R.id.input_edittxt);

        mSubmit = (TextView) findViewById(R.id.credit_account_register);

        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mKeyword = s.toString().trim();
                if (mKeyword.trim().length() > 0) {
                    mClearText.setVisibility(View.VISIBLE);
                } else {
                    mClearText.setVisibility(View.INVISIBLE);
                }
            }
        });

        FormInputView mNameInput = (FormInputView) findViewById(R.id.name_input);
        mNameEdit = (EditText) mNameInput.findViewById(R.id.input_edittxt);
        mNameInput.setDrawable(R.drawable.gjj_credit_username)
                .setHint("请输入登录名")
                .isClearView()
                .delete();
        FormInputView mCardInput = (FormInputView) findViewById(R.id.card_input);
        mCardEdit = (EditText) mCardInput.findViewById(R.id.input_edittxt);
        mCardInput.setDrawable(R.drawable.gjj_credit_username)
                .setHint("请输入密码")
                .isClearView()
                .delete();

        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshSubmit();
            }
        });

        mCardEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshSubmit();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                showToast("悬浮按钮");
                break;
            case R.id.iv_clear_text:
                mInputText.getText().clear();
                break;
            default:
                break;
        }
    }

    /**
     * 更新提交按钮
     */
    private void refreshSubmit() {
        if (TextUtils.isEmpty(mNameEdit.getText().toString().trim())
                || TextUtils.isEmpty(mCardEdit.getText().toString().trim())) {
            mSubmit.setClickable(false);
            mSubmit.setBackgroundResource(R.drawable.gjj_login_sendcode_disabled);
            mSubmit.setTextColor(ContextCompat.getColor(this, R.color.gjj_login_sendcode_disabled_color));
        } else {
            mSubmit.setClickable(true);
            mSubmit.setTextColor(ContextCompat.getColor(this, R.color.gjj_white));
            mSubmit.setBackgroundResource(R.drawable.gjj_login_submit_green_selector);
        }
    }


}

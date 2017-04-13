package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.caiyi.dailywork.R;


public class FloatingActivity extends BaseActivity implements View.OnClickListener{

    private EditText mInputText;

    private String mKeyword;

    /**
     * 清除按钮
     */
    private ImageView mClearText;

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
}

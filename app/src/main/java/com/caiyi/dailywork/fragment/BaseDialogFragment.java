package com.caiyi.dailywork.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.utils.NetWorkUtils;

/**
 * fragment 弹窗
 * <p>
 * Created by RZQ on 2017/6/22.
 */

public class BaseDialogFragment extends DialogFragment {
    /**
     * progressDialog
     */
    private Dialog mProgressDialog;

    /**
     * constructor
     */
    public BaseDialogFragment() {
        super();
        setStyle(DialogFragment.STYLE_NO_TITLE, 0); //设置样式
        setCancelable(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 封装toast，默认采用LENGTH_SHORT
     *
     * @param toast
     */
    protected void showToast(String toast) {
        if (null == getContext() || !isAdded() || !isDetached()) {
            return;
        }
        if (TextUtils.isEmpty(toast)) {
            Toast.makeText(getContext(), getString(R.string.error_toast), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int toast) {
        if (getContext() == null || !isAdded() || !isDetached()) {
            return;
        }
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测网络状态
     * @return
     */
    protected boolean isNetConnected() {
        if (getContext() == null) {
            return false;
        }
        if (NetWorkUtils.isNetorkConnected()) {
            return true;
        }
        showToast(getString(R.string.network_not_connected));
        return false;
    }

    protected void showDialog() {
        showLoadingDialog(true);
    }

    protected void showLoadingDialog(boolean cancelable) {
        if (null == mProgressDialog) {
            mProgressDialog = new Dialog(getContext());
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setContentView(R.layout.progress_dialog_content);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.isShowing();
        }
    }

    protected void dismissDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    //没有findViewById的View使用
    protected void setViewCilckListeners(View view, int...viewIds) {
        if (view == null) return;
        for (int id : viewIds) {
            if (id != 0) {
                view.findViewById(id).setOnClickListener((View.OnClickListener) this);
            }
        }
    }
    //已经findViewById,直接设置
    protected void setViewCilckListeners(View...views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener((View.OnClickListener) this);
            }
        }
    }


}

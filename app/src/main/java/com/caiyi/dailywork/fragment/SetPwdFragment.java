package com.caiyi.dailywork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.caiyi.dailywork.R;

/**
 * Created by RZQ on 2017/6/22.
 */

public class SetPwdFragment extends BaseDialogFragment implements View.OnClickListener{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDialog();
        View view = inflater.inflate(R.layout.login_setpwd, container, false);
        setViewCilckListeners(view, R.id.dialog_close);
        return view;
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.dialogEnter);
            window.setBackgroundDrawableResource(R.color.transparent);
        }
    }

    public static void showDialog(FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SetPwdFragment setPwdFragment = new SetPwdFragment();
        setPwdFragment.show(fragmentTransaction, "aaa");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                getDialog().dismiss();
                break;
        }
    }
}

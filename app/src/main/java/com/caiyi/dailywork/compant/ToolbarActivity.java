package com.caiyi.dailywork.compant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caiyi.dailywork.R;

public class ToolbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        initView();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.left_blue_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        toolbar.setNavigationIcon(R.mipmap.left_blue_arrow);
        ((TextView) findViewById(R.id.toolbar_title)).setText("首页");

        /**菜单的设置 */
//        ActionMenuView amv = (ActionMenuView) findViewById(R.id.action_menu_view);
//        getMenuInflater().inflate(R.menu.main, amv.getMenu());
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_tip :
                        showToast("提示");
                        break;
                    case R.id.item_menu:
                        showToast("菜单");
                        break;
                    case R.id.item_shop:
                        showToast("购物");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        Button mBtnDialog = (Button) findViewById(R.id.btn_dialog);
        mBtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ToolbarActivity.this);
                dialog.setTitle("this is Dialog");
                dialog.setMessage("Something is important");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("确认选择");
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("取消选择");
                    }
                });
                dialog.show();
            }
        });
    }
}

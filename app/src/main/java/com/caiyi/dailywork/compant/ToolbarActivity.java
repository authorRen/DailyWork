package com.caiyi.dailywork.compant;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    }
}

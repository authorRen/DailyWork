package com.caiyi.dailywork.compant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.adapter.DiscoveryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryActivity extends BaseActivity {

    private ListView mListView;

    private DiscoveryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        initView();

        initAdapterData();
    }

    private void initAdapterData() {
        adapter.updateData(initData(), true);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            data.add("测试数据：" + i);
        }
        return data;
    }

    private void initView() {
        mListView = ((ListView) findViewById(R.id.lv_discovery));
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, initData());
        adapter = new DiscoveryListAdapter(this.getLayoutInflater());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast(initData().get(position));
            }
        });
    }


}

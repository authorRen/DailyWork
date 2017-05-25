package com.caiyi.dailywork.compant;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.caiyi.dailywork.R;
import com.caiyi.dailywork.adapter.MyExpandableListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RZQ on 2017/5/24.
 */

public class ExpandableListViewActivity extends BaseActivity {

    private ExpandableListView listView;
    private MyExpandableListViewAdapter adapter;
    private List<String> parentList;
    HashMap<String, List<String>> dataset;
    private Button button;
    private List<String> childrenList1;
    private List<String> childrenList2;
    private List<String> childrenList3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        initToolbar();

        initData();

        initView();

    }

    private void initData() {
        dataset = new HashMap<>();
        parentList = new ArrayList<>();
        parentList.add("江苏");
        parentList.add("安徽");
        parentList.add("上海");
        childrenList1 = new ArrayList<>();
        childrenList2 = new ArrayList<>();
        childrenList3 = new ArrayList<>();

        childrenList1.add(parentList.get(0) + "-" + "南京");
        childrenList1.add(parentList.get(0) + "-" + "无锡");
        childrenList1.add(parentList.get(0) + "-" + "苏州");
        childrenList2.add(parentList.get(1) + "-" + "滁州");
        childrenList2.add(parentList.get(1) + "-" + "合肥");
        childrenList2.add(parentList.get(1) + "-" + "宿州");
        childrenList3.add(parentList.get(2) + "-" + "嘉定");
        childrenList3.add(parentList.get(2) + "-" + "徐汇");
        childrenList3.add(parentList.get(2) + "-" + "青浦");

        dataset.put(parentList.get(0), childrenList1);
        dataset.put(parentList.get(1), childrenList2);
        dataset.put(parentList.get(2), childrenList3);

    }

    private void initView() {
        button = (Button) findViewById(R.id.updateData);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                Toast.makeText(ExpandableListViewActivity.this, "数据已更新", Toast.LENGTH_SHORT).show();
            }
        });
        listView = (ExpandableListView) findViewById(R.id.ex_listView);
        listView.setGroupIndicator(null);

        adapter = new MyExpandableListViewAdapter(this, dataset, parentList);

        listView.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("列表");
    }

    /**
     * 更新数据
     */
    private void updateData() {
        childrenList1.clear();
        childrenList1.add(parentList.get(0) + "-" + "南京");
        childrenList1.add(parentList.get(0) + "-" + "无锡");
        childrenList1.add(parentList.get(0) + "-" + "苏州");
        childrenList1.add(parentList.get(0) + "-" + "常州");
        childrenList1.add(parentList.get(0) + "-" + "盐城");
        childrenList1.add(parentList.get(0) + "-" + "扬州");
        childrenList2.clear();
        childrenList2.add(parentList.get(1) + "-" + "滁州");
        childrenList2.add(parentList.get(1) + "-" + "合肥");
        childrenList2.add(parentList.get(1) + "-" + "宿州");
        childrenList2.add(parentList.get(1) + "-" + "亳州");
        childrenList2.add(parentList.get(1) + "-" + "马鞍山");
        childrenList2.add(parentList.get(1) + "-" + "芜湖");
        childrenList3.clear();
        childrenList3.add(parentList.get(2) + "-" + "嘉定");
        childrenList3.add(parentList.get(2) + "-" + "徐汇");
        childrenList3.add(parentList.get(2) + "-" + "青浦");
        childrenList3.add(parentList.get(2) + "-" + "宝山");
        childrenList3.add(parentList.get(2) + "-" + "浦东");
        childrenList3.add(parentList.get(2) + "-" + "普陀");
        adapter.notifyDataSetChanged();
    }
}

package com.wellee.annotation;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wellee.annotation.annotations.ContentView;
import com.wellee.annotation.annotations.InjectView;
import com.wellee.annotation.annotations.OnItemClick;
import com.wellee.annotation.annotations.OnItemLongClick;
import com.wellee.annotation.recyclerView.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : liwei
 * 创建日期 : 2019/12/20 13:39
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
@ContentView(R.layout.activity_recycler_view)
public class RecyclerViewActivity extends BaseActivity {

    @InjectView(R.id.rv)
    private CustomRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RvAdapter adapter = new RvAdapter(this, initData());
        adapter.bindRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("条目" + i);
        }
        return data;
    }

    @OnItemClick(R.id.rv)
    public void itemClick(View view, int position) {
        Toast.makeText(this, "点击了position = " + position, Toast.LENGTH_SHORT).show();
    }

    @OnItemLongClick(R.id.rv)
    public boolean itemLongClick(View view, int position) {
        Toast.makeText(this, "长按了position = " + position, Toast.LENGTH_SHORT).show();
        return true;
    }
}

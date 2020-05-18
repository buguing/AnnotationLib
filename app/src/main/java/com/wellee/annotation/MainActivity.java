package com.wellee.annotation;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wellee.annotation.annotations.ContentView;
import com.wellee.annotation.annotations.InjectView;
import com.wellee.annotation.annotations.OnClick;
import com.wellee.annotation.annotations.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectView(R.id.btn)
    private Button btn;

    @InjectView(R.id.tv)
    private TextView tv;

    @OnClick({R.id.btn, R.id.tv})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Toast.makeText(this, "btn click---->" + btn.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.tv:
                Toast.makeText(this, "tv click---->" + tv.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnLongClick({R.id.btn, R.id.tv})
    public boolean longClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Toast.makeText(this, "btn longClick---->", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv:
                Toast.makeText(this, "tv longClick---->", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

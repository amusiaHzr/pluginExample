package com.amusia.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity.setContentView(R.layout.activity_plugin);
        Toast.makeText(parentActivity, "我是插件", Toast.LENGTH_SHORT).show();
        parentActivity.findViewById(R.id.btn_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.startActivity(new Intent(parentActivity, PluginSecondActivity.class));
            }
        });
    }
}

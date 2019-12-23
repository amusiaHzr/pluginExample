package com.amusia.plugin;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PluginSecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity.setContentView(R.layout.activity_second_plugin);
        parentActivity.findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
            }
        });

        parentActivity.findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parentActivity, PluginService.class);
                parentActivity.startService(intent);
            }
        });

        parentActivity.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("PluginReceiver");
                parentActivity.registerReceiver(new PluginReceiver(), intentFilter);
            }
        });
        parentActivity.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("PluginReceiver");
                parentActivity.sendBroadcast(intent);
            }
        });
        parentActivity.findViewById(R.id.btn_send_static).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("PluginStaticReceiver");
                parentActivity.sendBroadcast(intent);
            }
        });
    }
}

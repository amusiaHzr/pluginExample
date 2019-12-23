package com.amusia.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.amusia.stander.ReceiverInterFace;

public class PluginReceiver extends BroadcastReceiver implements ReceiverInterFace {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件，收到广播", Toast.LENGTH_SHORT).show();
    }
}

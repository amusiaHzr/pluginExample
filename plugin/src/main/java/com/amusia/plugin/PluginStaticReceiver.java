package com.amusia.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.amusia.stander.ReceiverInterFace;

public class PluginStaticReceiver extends BroadcastReceiver implements ReceiverInterFace {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件里面的静态广播，收到广播", Toast.LENGTH_SHORT).show();
    }
}

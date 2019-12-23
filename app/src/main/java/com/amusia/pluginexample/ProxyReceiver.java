package com.amusia.pluginexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.amusia.stander.ReceiverInterFace;

import dalvik.system.DexClassLoader;

public class ProxyReceiver extends BroadcastReceiver {

    String className;

    public ProxyReceiver(String className) {
        this.className = className;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            DexClassLoader dexClassLoader = PluginManager.getInstance(context).getDexClassLoader();
            Class<?> aClass = dexClassLoader.loadClass(className);
            Object o = aClass.newInstance();
            ReceiverInterFace receiverInterFace = (ReceiverInterFace) o;
            receiverInterFace.onReceive(context, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

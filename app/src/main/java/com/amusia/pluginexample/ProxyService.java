package com.amusia.pluginexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.amusia.stander.ServiceInterFace;

import androidx.annotation.Nullable;

public class ProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            String pluginServiceName = intent.getStringExtra("className");
            Class<?> aClass = PluginManager.getInstance(this).getDexClassLoader().loadClass(pluginServiceName);
            Object o = aClass.newInstance();
            ServiceInterFace serviceInterFace = (ServiceInterFace) o;
            serviceInterFace.insertAppContext(this);
            serviceInterFace.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

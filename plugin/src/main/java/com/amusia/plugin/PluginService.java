package com.amusia.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.amusia.stander.ServiceInterFace;

import androidx.annotation.Nullable;

public class PluginService extends Service implements ServiceInterFace {

    private final String TAG = PluginService.class.getSimpleName();
    Service appService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void insertAppContext(Service appService) {
        this.appService = appService;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启子线程，执行耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Log.e(TAG,"插件里面的服务 正在执行中....");
                        Handler handlerThree=new Handler(appService.getMainLooper());
                        handlerThree.post(new Runnable(){
                            public void run(){
                                Toast.makeText(appService.getApplicationContext() ,"插件里面的服务 正在执行中....",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }

        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}

package com.amusia.pluginexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import com.amusia.stander.ActivityInterFace;

import androidx.annotation.Nullable;
import dalvik.system.DexClassLoader;

/**
 * @作者 huzhuoren
 * @创建日期 2019-12-20 16:58
 * @描述: 加载plugin的activity
 */
public class ProxyActivity extends Activity { //这里需要注意不能用 AppCompatActivity，会报setWindowCallback异常

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            String className = getIntent().getStringExtra("className");
            //得到类加载器
            DexClassLoader dexClassLoader = PluginManager.getInstance(this).getDexClassLoader();
            //加载plugin中的类
            Class<?> aClass = dexClassLoader.loadClass(className);

            //实例化
            Object o = aClass.newInstance();

//            //实例化
//            Constructor constructor = aClass.getConstructor(new Class[]{});
//            Object o = constructor.newInstance(new Object[]{});


            //强转成接口
            ActivityInterFace activityInterFace = (ActivityInterFace) o;
            //注入宿主环境
            activityInterFace.insertActivity(this);
            //启用onCreate方法

            if (savedInstanceState == null)
                savedInstanceState = new Bundle();
            savedInstanceState.putString("proxyActivity", "我是proxyActivity");

            activityInterFace.onCreate(savedInstanceState);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startActivity(Intent intent) {
        //获取到plugin传递过来的类名字
        String className = intent.getComponent().getClassName();
        Intent intentNew = new Intent(this, ProxyActivity.class);
        intentNew.putExtra("className", className);
        //因为名字相同，这里需要调用super的startActivity，否则会递归调用当前startActivity
        super.startActivity(intentNew);
    }

    public ComponentName startService(Intent intent) {

        //获取到plugin传递过来的类名字
        String className = intent.getComponent().getClassName();
        Intent intentNew = new Intent(this, ProxyService.class);
        intentNew.putExtra("className", className);
        return super.startService(intentNew);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        String className = receiver.getClass().getName();
        return super.registerReceiver(new ProxyReceiver(className), filter);
    }


    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }

    // 一定要重写，才能加载到plugin的资源文件
    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }
}

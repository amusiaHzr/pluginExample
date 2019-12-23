package com.amusia.pluginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void loadPlugin(View view) {
        PluginManager.getInstance(this).loadPlugin();
    }

    public void start(View view) {
        try {
            //获取插件包地址
            String path = PluginManager.getInstance(this).getApkPath();
            // 获取PackageManager
            PackageManager packageManager = getPackageManager();
            //通过PackageManager获取目标apk文件中的信息
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            //得到第一个activity信息
            ActivityInfo activityInfo = packageArchiveInfo.activities[0];
            //得到 activity名字,全路径名字
            String pluginActivityName = activityInfo.name;

            Intent intent = new Intent(this, ProxyActivity.class);
            intent.putExtra("className", pluginActivityName);
            startActivity(intent);
        } catch (Exception ignored) {
            Toast.makeText(this, "没有plugin", Toast.LENGTH_SHORT).show();
        }

    }
}

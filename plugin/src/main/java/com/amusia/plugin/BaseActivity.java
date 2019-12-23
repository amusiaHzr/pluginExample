package com.amusia.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.amusia.stander.ActivityInterFace;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @作者 huzhuoren
 * @创建日期 2019-12-20 16:56
 * @描述: 插件Activity基类 宿主的context从这里注入
 */

public class BaseActivity extends Activity implements ActivityInterFace {

    Activity parentActivity;

    @Override
    public void insertActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

//    @Override
//    public void startActivity(Intent intent) {
//        Intent intentNew = new Intent();
//        intentNew.putExtra("className", intent.getComponent().getClassName());
//        parentActivity.startActivity(intentNew);
//    }

}

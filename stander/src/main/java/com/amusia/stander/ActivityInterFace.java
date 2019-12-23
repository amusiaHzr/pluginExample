package com.amusia.stander;

import android.app.Activity;
import android.os.Bundle;

/**
 * @作者 huzhuoren
 * @创建日期 2019-12-20 16:49
 * @描述: activity 接口
 */
public interface ActivityInterFace {

    void insertActivity(Activity parentActivity);

    void onCreate(Bundle savedInstanceState);

    void onResume();

    void onStop();

    void onDestroy();
}

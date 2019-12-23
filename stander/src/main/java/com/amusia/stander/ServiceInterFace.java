package com.amusia.stander;

import android.app.Service;
import android.content.Intent;

public interface ServiceInterFace {

    void insertAppContext(Service appService);

    public void onCreate();

    public int onStartCommand(Intent intent, int flags, int startId);

    public void onDestroy();

}

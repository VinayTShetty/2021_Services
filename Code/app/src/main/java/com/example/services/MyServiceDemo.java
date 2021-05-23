package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyServiceDemo extends Service {


    private static final String APPLICATION_TAG=MyServiceDemo.class.getSimpleName();
    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    /**
     *This method is depricated.
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    /**
     * This method will get executed when ever we start the Service.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: "+Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+Thread.currentThread().getId());
    }
}

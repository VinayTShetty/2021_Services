package com.example.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;

import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyServiceDemo extends IntentService{

    private static final String APPLICATION_TAG=MyServiceDemo.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;
    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public MyServiceDemo(){
        super(MyServiceDemo.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /**
         * startForeground(1, getNotification())
         * Use this to start the Service.In the Foreground
         */
        startForeground(1, getNotification());
        mIsRandomGeneratorOn =true;
        startRandomNumberGenerator();
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.d(TAG,"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.d(TAG,"Thread Interrupted");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn=false;
        Log.d(TAG,"onDestroy() thread Id: "+Thread.currentThread().getId());
    }

    private Notification getNotification(){
        return MyApplication.getMyAppsNotificationManager().getNotification(MainActivity.class,
                "BackgroundService running",
                1,
                false,
                1);
    }
}
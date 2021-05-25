package com.example.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyServiceDemo extends IntentService {


    private static final String APPLICATION_TAG=MyServiceDemo.class.getSimpleName();
    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;


    /**
     * By defualt IntentService will generate the one Argument constructor.
     * So i have changed the one Argument Constructor  to zero Argument constructor.
     */
    public MyServiceDemo() {
        super(MyServiceDemo.class.getSimpleName());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        /**
         * By default it will run on the different Thread.
         */
        mIsRandomGeneratorOn=true;
        startRandomNumberGenerator();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.d(TAG,"Service Destroyed");
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(100); //making Thread to sleep for one Seconds.So that Random generation Number will not be fast.
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.d(TAG,", Random Number: "+ mRandomNumber+" Thread Id= "+Thread.currentThread().getId());
                }
            }catch (InterruptedException e){
                Log.d(TAG,"Thread Interrupted");
            }

        }
    }

    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn =false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }
}

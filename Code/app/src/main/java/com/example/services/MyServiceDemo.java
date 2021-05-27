package com.example.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import java.util.Random;

public class MyServiceDemo extends JobIntentService {


    private static final String APPLICATION_TAG=MyServiceDemo.class.getSimpleName();
    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;
    private static int JOB_ID=101;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        mIsRandomGeneratorOn=true;
        startRandomNumberGenerator();
        Log.d(TAG, "onHandleWork: Thread Id= "+Thread.currentThread().getId());
    }

    public static void enqueQueWork_loc(Context context,Intent intent){
            enqueueWork(context,MyServiceDemo.class,JOB_ID,intent);
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork: Thread Id= "+Thread.currentThread().getId());
        return super.onStopCurrentWork();// by Default it will return true value.
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
                Thread.sleep(1000); //making Thread to sleep for one Seconds.So that Random generation Number will not be fast.
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.d(TAG,"startRandomNumberGenerator Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
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

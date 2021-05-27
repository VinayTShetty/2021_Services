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

    private final int MIN=0;
    private final int MAX=100;
    private static int JOB_ID=101;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: Thread Id= "+Thread.currentThread().getId());
        startRandomNumberGenerator(intent.getIntExtra(getResources().getString(R.string.COUNT_TAG),-1));
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
        Log.d(TAG,"Service Destroyed");
    }


    private void startRandomNumberGenerator(int countWork){
        for (int i = 0; i <5; i++) {
            try{
                if(isStopped()){
                    Log.d(TAG, "startRandomNumberGenerator: ServiceStooped In Middle "+isStopped());
                    return;
                }
                Thread.sleep(1000); //making Thread to sleep for one Seconds.So that Random generation Number will not be fast.
                mRandomNumber =new Random().nextInt(MAX)+MIN;
                Log.d(TAG,"startRandomNumberGenerator Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber+" Count Work= "+countWork);

            }catch (InterruptedException e){
                Log.d(TAG,"Thread Interrupted");
            }
        }
        stopSelf();
        Log.d(TAG, "startRandomNumberGenerator: ServiceStopped Thread Id= "+Thread.currentThread().getId());
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }
}

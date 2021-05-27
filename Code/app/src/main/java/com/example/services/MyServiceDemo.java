package com.example.services;

import android.app.IntentService;
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
public class MyServiceDemo extends JobService {


    private static final String APPLICATION_TAG = MyServiceDemo.class.getSimpleName();
    private static final String COMMON_TAG = "COMMON_TAG " + APPLICATION_TAG;
    private static final String TAG = COMMON_TAG;

    private int mRandomNumber;

    private final int MIN = 0;
    private final int MAX = 100;
    private boolean mIsRandomGeneratorOn;


    @Override
    public boolean onStartJob(JobParameters params) {
        /**
         * Logic implemented in this method by default will Run on the MainThread/UI Thread.
         */
        Log.d(TAG, "onStartJob: Thread ID= " + Thread.currentThread().getId());
        doBackgroundWork();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: Thread Id= "+Thread.currentThread().getId());
        return false;// want to ReSchedule the job,so return type is true.
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn=false;
        Log.d(TAG, "Service Destroyed Thread Id= "+Thread.currentThread().getId());
    }

    private void doBackgroundWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "doBackgroundWork ");
                mIsRandomGeneratorOn = true;
                startRandomNumberGenerator();
            }
        }).start();
    }


    private void startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000); //making Thread to sleep for one Seconds.So that Random generation Number will not be fast.
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;
                    Log.d(TAG, "startRandomNumberGenerator Thread id: " + Thread.currentThread().getId() + ", Random Number: " + mRandomNumber);
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "Thread Interrupted");
            }
        }

    }

    public int getRandomNumber() {
        return mRandomNumber;
    }


}

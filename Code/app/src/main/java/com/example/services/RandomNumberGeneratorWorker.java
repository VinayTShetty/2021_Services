package com.example.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class RandomNumberGeneratorWorker extends Worker {

    private static final String APPLICATION_TAG=RandomNumberGeneratorWorker.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    Context context;
    WorkerParameters workerParameters;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public RandomNumberGeneratorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParameters = workerParams;
        mIsRandomGeneratorOn = true;
    }

    @NonNull
    @Override
    public Result doWork() {
        /**
         * Implementation logic should be written here,what work should happen.
         */
        startRandomNumberGenerator();
        return Result.success();
    }

    @Override
    public void onStopped() {
        /**
         * This method is optional,
         * In Case we need to handle when the work gets stopped ,ex:-Suppose we need to get the Random number when it gets stopped.
         */
        super.onStopped();
        Log.d(TAG,"Worker has been cancelled");
    }

    private void startRandomNumberGenerator(){
        int i=0;
        while (i<5 && !isStopped()){   // isStopped() this will return true if we have explicitly stoped the work.
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.d(TAG,"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                    i++;
                }
            }catch (InterruptedException e){
                Log.d(TAG,"Thread Interrupted");
            }
        }
    }
}

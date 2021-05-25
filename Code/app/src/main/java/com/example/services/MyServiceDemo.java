package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyServiceDemo extends Service {


    private static final String APPLICATION_TAG=MyServiceDemo.class.getSimpleName();
    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;


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
        Log.d(TAG,"In onStartCommend, thread id: "+Thread.currentThread().getId());
        mIsRandomGeneratorOn =true;
        new Thread(new Runnable() {  // using a different Thread because service by default will run on the main Thread.So avoid ANR using different thread.
            @Override
            public void run() {
                //startRandomNumberGenerator();
            }
        }).start();

        startRandomNumberGenerator();
        return START_STICKY;
    }

    /**
     * Main Part.
     * Implementing onBind Method,which will return Ibinder interface.
     * Pre-Requisites
     * --------------
     * 1)Create a class which extends Binder
     * 2)OverRide a method getService which return service instance.
     * 3)Create a IBinder instance intializing a class that extends Binder.(i.e )-->{ private IBinder mBinder=new MyServiceBinder(); }
     * 4)Return IBinder instance variable to onBind method.
     */

    class  MyServiceBinder extends Binder{ // Binder is in abstract class.so we can eiether implement or extend the Binder class.
        public MyServiceDemo getService(){
           return MyServiceDemo.this;
        }
    }

    private IBinder mBinder=new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
                    Log.d(TAG,"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
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

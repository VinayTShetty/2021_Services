package com.example.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;
    
    
    /**
     * Android always recomends to use explicit intent to start the Service.
     */
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeService();
    }

    private void intializeService() {
        serviceIntent=new Intent(this,MyServiceDemo.class);
    }

    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
            startService(serviceIntent);
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: ");
            stopService(serviceIntent);
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
Binding a Service to Activity to get a Number
----------------------------------------------
1)Service needs to implements onBind which return IBinder.
2)Activity will use Service Connection API to connect to the Service.
*/



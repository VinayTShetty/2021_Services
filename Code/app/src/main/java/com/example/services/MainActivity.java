package com.example.services;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;
    /**
     * Android always recomends to use explicit intent to start the Service.
     */
    Intent serviceDemoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeServiceIntent();
    }

    private void intializeServiceIntent() {
        serviceDemoIntent=new Intent(this,MyServiceDemo.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
        ContextCompat.startForegroundService(this,serviceDemoIntent);
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: Thread ID= "+Thread.currentThread().getId());
        stopService(serviceDemoIntent);
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
ForeGround Service:-
====================

1)
In JobScheduler,JobIntent Service when ever we kill the application the service will stop and then it will Restart.
Suppose we don t want the service to get stopped,we want the Service to run continously in the background.
Then the best choice is foreground Service.

2)Forground Service performs opertation which are noticable to the user.(via notification)

Implement Forground Service:-
-----------------------------
1)Add permission in the manifest
uses-permission android:name="android.permission.FOREGROUND_SERVICE"
2)Declare foregroundServiceType in manifest
       <service
            android:name=".MyServiceDemo"
            android:foregroundServiceType="dataSync" />

Note:-Even though the we wont provide the foregroundServiceType in the manifest the service will run.
But as per the documentation we need to provide it.

We can use dataSync most the time,As it will work.If we are not doing anything complicated with respect to Media.

3)
startForeground(1, getNotification());
Should be called inside the service,when we start the service.
4)
We can start the service using.
ContextCompat.startForegroundService(this,serviceDemoIntent);


*/
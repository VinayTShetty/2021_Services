package com.example.services;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    private WorkRequest workRequest;
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeWorkManager();
        intializeWorkRequestManager();
    }

    private void intializeWorkManager() {
        workManager = WorkManager.getInstance(getApplicationContext());
    }
    private void intializeWorkRequestManager(){
        //workRequest = OneTimeWorkRequest.from(RandomNumberGeneratorWorker.class);     // Using OneTimeWorkRequest also is possible.
        workRequest = new PeriodicWorkRequest.Builder(RandomNumberGeneratorWorker.class, 15, TimeUnit.MINUTES).build();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
        /**
         * To start the background task use
         * workManager.enqueue(workRequest);
         */
        workManager.enqueue(workRequest);
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: Thread ID= "+Thread.currentThread().getId());
        /**
         * we can cancel the work by many ways.
         *  In this example we are cancelling the work using Id.
         */
        workManager.cancelWorkById(workRequest.getId());
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
Work manager:-
==============

Services Summary:-
-----------------

1)Services
2)Intent Service
3)JobIntentService
4)JobScheduler/JobService
5)ForegroundServices

Pre-Oreo Services
----------------
Services
Intent Service

Post Oreo Services
------------------
JobIntentService
JobScheduler/JobService
ForegroundServices


Workmanager Definition:-An API that makes easy to schedule deferable,asynchronous task that are expected to run reliably.

Definition Meaning:-

deferable i.e
*************
A very Controlled type mechanism.(Scheduled Mechanism)
Run one time
Run Mulitple Time
Compact with Doze Mode,Power Saving Mode

reliably
********
Run only when the Device is connected to Wifi.
when the device is idle
when the Device has sufficient storage space

Always finish the work started.
------------------------------
Even if app exits
Even if app restarts.

asynchronous task
*****************
Something that run s in the backgroud.

Implementation of WorkManager:-
+++++++++++++++++++++++++++++++
1)
extend a class Worker
after extending a class we will be having a callback methods
doWork()
onStoped()

doWork():- This will be executed when we start the work.
onStoped():-This will be executed when we stop the work

2)
We use Constraint API,where we declare all the  Constraints.
a)Network Type
b)Battery Low
c)Requires Charging
d)Device idle
f)Storage Low
h)Time Constraint :-Minimum we should give 15 mins.(Even in JobScheduler Minimum time interval is 15 mins).

3)
Workrequest:-Its a abstract class which contains lots of Configuration like
a)oneTimeWorkRequest()
b)PeriodicWorkRequest()

4)
WorkManager:-
1)EnqueUeing the work
2)Cancelling the work


Summary:-With Work manager we can make assurane that,work will run as per the setting provided.

*/
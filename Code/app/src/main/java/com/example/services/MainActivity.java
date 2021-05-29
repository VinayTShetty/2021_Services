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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;

    private WorkManager workManager;
    private OneTimeWorkRequest workRequest1, workRequest2, workRequest3;

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
        workRequest1 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker1.class).addTag("worker1").build();
        workRequest2 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker2.class).addTag("worker2").build();
        workRequest3 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker3.class).addTag("worker3").build();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
        /**
         * Enqueing all the works one aftet the other
         */
        //Note:- chain ing the same work will not eecute i guesss..Previous commmit is not working..(Commit name:-Test case of Canceling WorkRequest when before completing the previous workRequest.)
        WorkManager.getInstance(getApplicationContext()).beginWith(Arrays.asList(workRequest1,workRequest2)).then(workRequest2).then(workRequest3).enqueue();
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: Thread ID= "+Thread.currentThread().getId());
        /**
         * we can cancel the work by many ways.
         *  In this example we are cancelling the work using Id.
         */
        workManager.cancelAllWorkByTag("worker3");
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
Work manager:-
==============

Executing Work Managers Parallely.
---------------------------------
Pic is posted in the notes

WorkManager Enqueing logic:-
If we are enqueing

 example:-
 WorkManager.getInstance(getApplicationContext()).beginWith(workRequest1).then(workRequest2).then(workRequest3).enqueue();

 workRequest1--->Executes frist
 workRequest2--->Executes second
 workRequest3--->Executes third

 Suppose assume  workRequest2 is in process and user cancelled  workRequest2.
 Then  workRequest3 won t be executed.
 workRequest3 will execute only after  workRequest2 is comppleted.

 Note:-
 Work Request chaining is allowed only for OneTimeWorkRequest.
 Periodic WorkRequest chaning is not yet given any documentation to execute.


 Test Case:-

 WorkManager.getInstance(getApplicationContext()).beginWith(Arrays.asList(workRequest1,workRequest2)).then(workRequest2).then(workRequest3).enqueue();

 workRequest1 and workRequest2 will run in parallel
 then workRequest2 will execute,
 later workRequest3 will execute at the end.
 After executing workRequest1 and workRequest2 if user cancels workRequest2,then workRequest3 will not execute bcoz workRequest3 will execute only after workRequest2 is finished.
 but workRequest2 is not completed.(Due to cancellation).so workRequest3 won t be executed.

*/
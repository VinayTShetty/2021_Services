package com.example.services;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
   // private Intent serviceIntent;
    TextView random_number_TextView;
    JobScheduler jobScheduler;
    private int JOB_ID=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // intializeService();
        intiazeViews();
        intializeJobScheduler();
    }

    private void intializeJobScheduler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        }
    }

    private void intiazeViews() {
        random_number_TextView=(TextView)findViewById(R.id.random_number_TextView_Id);
    }

    private void intializeService() {
     //   serviceIntent=new Intent(this,MyServiceDemo.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
        startJob();
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: Thread ID= "+Thread.currentThread().getId());
        stopJob();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startJob(){
        ComponentName componentName = new ComponentName(this, MyServiceDemo.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
                .setPeriodic(15*60*1000)
                .setRequiresCharging(false)
                .setPersisted(true)
                .build();

        if(jobScheduler.schedule(jobInfo)==JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "MainActivity thread id: " + Thread.currentThread().getId()+", job successfully scheduled");
        }else {
            Log.d(TAG, "MainActivity thread id: " + Thread.currentThread().getId()+", job could not be scheduled");
        }
    }


    private void stopJob(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler.cancel(JOB_ID);
        }
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
JobScheduler/JobService

1)onStartJob method will get called as soon as the service gets called.
2)Add BIND_JOB_SERVICE to the Manifest file for the Service Tag.
3)When we stop the service explicity by calling (jobScheduler.cancel(JOB_ID)) it wont reschedule again.
*/
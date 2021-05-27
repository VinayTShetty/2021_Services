package com.example.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;
    /**
     * Android always recomends to use explicit intent to start the Service.
     */
    private Intent serviceIntent;
    TextView random_number_TextView;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeService();
        intiazeViews();
    }

    private void intiazeViews() {
        random_number_TextView=(TextView)findViewById(R.id.random_number_TextView_Id);
    }

    private void intializeService() {
        serviceIntent=new Intent(this,MyServiceDemo.class);
    }

    public void startService(View view) {
        Log.d(TAG, "startService: Thread ID= "+Thread.currentThread().getId());
        /**
         * We cannot start the service using startService() method as usual we have call invoke enquqeuqe method.
         */
        serviceIntent.putExtra(getResources().getString(R.string.COUNT_TAG),++count);
        MyServiceDemo.enqueQueWork_loc(this,serviceIntent);

    }

    public void stopService(View view) {
        Toast.makeText(this,"Job Intent Service Cannot be Stop Explicitly",Toast.LENGTH_SHORT).show();
    }
}


/*
Notes Link
https://docs.google.com/document/d/1it6NAM5izAovZzufaKGVemZ884ibT730QsDJOfFOrCs/edit#heading=h.tqrsf9imesev
*/

/*
JobIntent Service Test Cases
----------------------------

1)
   a)JobIntentService should be stopped by usig stopSelf()
   b)OS needs to stop it due to some resource crunch situation.
   c)Assigned Work is finished

2)If Service is stoped depending on the return type of onStopCurrentWork the service will reschedule



*/
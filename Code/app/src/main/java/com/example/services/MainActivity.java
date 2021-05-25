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

public class MainActivity extends AppCompatActivity {


    private static final String APPLICATION_TAG=MainActivity.class.getSimpleName();

    private static final String COMMON_TAG="COMMON_TAG "+APPLICATION_TAG;
    private static final String TAG=COMMON_TAG;
    private  MyServiceDemo myServiceDemo;
    private  boolean isServiceBound;
    private ServiceConnection serviceConnection;

    /**
     * Android always recomends to use explicit intent to start the Service.
     */
    private Intent serviceIntent;
    TextView random_number_TextView;

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
        startService(serviceIntent);
    }

    public void stopService(View view) {
        Log.d(TAG, "stopService: ");
        stopService(serviceIntent);
    }

    public void bindService(View view) {
        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    MyServiceDemo.MyServiceBinder myServiceBinder =(MyServiceDemo.MyServiceBinder)binder;
                    myServiceDemo=myServiceBinder.getService();
                    isServiceBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound=false;
                }
            };
            bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void unBindService(View view) {
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound=false;
        }
    }

    public void getRandomNumber(View view) {
        if(isServiceBound){
            random_number_TextView.setText("Random Number= "+myServiceDemo.getRandomNumber());
        }else {
            random_number_TextView.setText("Service is Not Bounded");
        }
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


Implementation of a Service Connection API to the component which wants to connect to the service
-------------------------------------------------------------------------------------------------
1)Create a instance of the Service class.
2)Use  a ServiceConnection by intializing it as inner class by overRiding two methods.
    1)onServiceConnected
    2)onServiceDisconnected
3)Intialize the instance of the Service class which was created.
    Note:-Intialize in the onServiceConnected method using the IBinder variable reference.
          MyServiceDemo.MyServiceBinder myServiceBinder =(MyServiceDemo.MyServiceBinder)binder;
          myServiceDemo=myServiceBinder.getService();
4)user predefined method bindService() method to bind to the service.
        parameters needs to be passed to the bindService() are bindService( serviceintent,serviceConnection,BIND_AUTO_CREATE)

      serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };



Test cases:-
------------
1)Component can bind to the Service,even though service has not started.
2)We cannot stop the Bound Service using stopSelf/stopService(serviceInent).
  Bound Service will stop when all the components will unbind from the Service.
3)We can Bind the Component to the service only once,once bound clicking on Bind Service again and again will have no effect.
 Component will not bind again and again once if its bounded.


 Bound Service Behaviour:-
 ------------------------

1)Components can bind to StartedService,UnStarted Service,Stopped Service
2)Any number of components can bind to the service
3)Bound Service cannot be destroyed(exception resource crunch)
4)Note:-***
 If stop Service is called while service was bound,it will stopped automatically,when all the components from the service are unbound.
5)components which can bound to the service are
    Activity,Service,Content Provider(BroadCast Reciever cannot be used to bound to the service)



-----------------------------------------------------------------
Bound Service with Respect to Intent Service Demo In this Commit

DisAdvantage:-
In Case of Intent Service,If we put the application back to the background.
It will stop after a while.So overCome this scenario we will follow differet approoach.
-----------------------------------------------------------------


*/



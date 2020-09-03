package com.elamed.timerclock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Time;

public class MyService extends Service {

    @Override
    public void onCreate() {
        Log.e("Service","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service","onStartCommand");
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        Intent intent1 = registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("Service","tick tick blaty "+ System.currentTimeMillis() );
                Toast.makeText(context,"tick tick blaty", Toast.LENGTH_SHORT).show();
            }
        }, intentFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e("Service","Destroy");
        super.onDestroy();
    }
}

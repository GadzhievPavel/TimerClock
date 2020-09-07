package com.elamed.timerclock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private List<Long> times;
    private QueryDB queryDB;
    @Override
    public void onCreate() {
        Log.e("Service","onCreate");
        queryDB = new QueryDB(getApplicationContext());
        times = queryDB.selectAllTime();
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service","onStartCommand");
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"my channel",NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("My channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title").setContentText("Text");
        Notification notification = builder.build();
        notificationManager.notify(1,notification);
        startForeground(1001,notification);


        for (Long time : times){
            if(time > System.currentTimeMillis()){
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerClass.class).
                        setInitialDelay(time-System.currentTimeMillis(), TimeUnit.MILLISECONDS).build();
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        }
        return START_STICKY;
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

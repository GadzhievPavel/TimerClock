package com.elamed.timerclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RebootReceiver extends BroadcastReceiver {
    QueryDB queryDB;
    @Override
    public void onReceive(Context context, Intent intent) {
        WorkManager.getInstance().cancelAllWork();
        queryDB = new QueryDB(context);
        try {
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            Toast.makeText(context, technology+" "+plugged+" "+scale+" "+health, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "battary", Toast.LENGTH_SHORT).show();
//        List<Long> times = queryDB.selectAllTime();
//        int i=0;
//        for (Long  time : times){
//            i++;
//            if(time > System.currentTimeMillis()){
//                Data.Builder builder = new Data.Builder();
//                builder.putInt("name",i);
//                Data data = builder.build();
//                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerClass.class).
//                        setInitialDelay(time-System.currentTimeMillis(), TimeUnit.MILLISECONDS).setInputData(data)
//                        .build();
//                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
//            }
//        }
//        Toast.makeText(context, "REBOOT", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Error Battary Changed", Toast.LENGTH_SHORT).show();
        }


    }
}

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
        context.startService(new Intent(context,MyService.class));
    }
}

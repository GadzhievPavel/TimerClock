package com.elamed.timerclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.ACTION_TIME_TICK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TimePicker timePicker;
    private RecyclerView recyclerView;
    private QueryDB queryDB;
    private List<Long> times;
    private AdapterRecycler adapterRecycler;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        queryDB = new QueryDB(this);
        context =getApplicationContext();
        button = findViewById(R.id.save);
        timePicker = findViewById(R.id.time_picker);
        recyclerView = findViewById(R.id.recycler);
        times = queryDB.selectAllTime();
        timePicker.setIs24HourView(true);
        adapterRecycler = new AdapterRecycler(this,times);
        button.setOnClickListener(this);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                LocalDateTime localDateTime = new LocalDateTime(LocalDateTime.now().getYear(),LocalDateTime.now().getMonthOfYear(),
                        LocalDateTime.now().getDayOfMonth(),hour,minute);
                LocalDate localDate = new LocalDate();
                Date date = new Date(localDate.toDate().getTime()+localDateTime.getMillisOfDay());
                Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
                queryDB.insertTime(date.getTime());
                times.add(date.getTime());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startService(new Intent(getApplicationContext(),MyService.class));
                }
                break;
        }
    }

    private boolean isServiceRunning(Class<?> service){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(service.getName().equals(serviceInfo.service.getClassName())){
                return true;
            }
        }
        return  false;
    }
}

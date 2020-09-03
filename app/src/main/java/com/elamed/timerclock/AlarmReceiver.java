//package com.elamed.timerclock;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//
//public class AlarmReceiver extends BroadcastReceiver {
//
//    Context context;
//    Uri ringtoneUri;
//    String text;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        this.context = context;
//        Log.e("ALARM", String.valueOf(System.currentTimeMillis()));
//        text = intent.getAction();
//        int request = intent.getIntExtra("request",0);
//        notificationDialog(request);
//    }
//
//    private void notificationDialog(int requestCode) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            @SuppressLint("WrongConstant") NotificationChannel notificationChannel =
//                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
//                            NotificationManager.IMPORTANCE_MAX);
//            // Configure the notification channel.
//            notificationChannel.setDescription("Sample Channel description");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000,0, 1000, 500, 1000});
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            notificationChannel.enableVibration(true);
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        Intent intent = new Intent(context,AlarmNotification.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,requestCode,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("Tutorialspoint")
//                .setPriority(Notification.PRIORITY_MAX)
//                .setContentTitle("Время для процедуры")
//                .setContentText("Лечим короновирус "+requestCode)
//                .setContentInfo("Быстро")
//                .addAction(R.drawable.ic_action_name1,"Открыть",pendingIntent)
//                .setSound(ringtoneUri);
//        assert notificationManager != null;
//        notificationManager.notify(requestCode, notificationBuilder.build());
//    }
//
//}

package com.edikorce.albaniannews.utilities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.edikorce.albaniannews.R;
import com.edikorce.albaniannews.activities.MainActivity;

public class AndroidSystemUtilities {

    Context context;


    public static final String NOTIFICATION_CHANNEL = "com.sda.gazetatshqip";


    public AndroidSystemUtilities(Context context) {
        this.context = context;
    }

    public static void createNotificationChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL,
                    "news_notification_channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static void notify(Context context, String title, String message){

        // hyn ne mainactivity kur shtypet notification.
        Intent main_activity = new Intent(context, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, main_activity, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "com.sda.gazetatshqip");
        notificationBuilder.setSmallIcon(R.drawable.newspaper);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setAutoCancel(true);



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notificationBuilder.build());

    }

    public static void showToast(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static Notification serviceNotification(Context context){
        // notification kur luhet servici

        return new NotificationCompat.Builder(context, "com.sda.gazetatshqip")
                .setContentTitle("GazetatShqiptare")
                .setContentText("po rifreskon lajmet")
                .setSmallIcon(R.drawable.newspaper)
                .build();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void startScrapService(Context context){

        if (isNetworkConnected(context)){
            Intent intent = new Intent(context.getApplicationContext(), ScrapService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            }
        }else{
            showToast(context, "Pajisja nuk ka internet\n Lajmet nuk u rifreskuan");
        }

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}

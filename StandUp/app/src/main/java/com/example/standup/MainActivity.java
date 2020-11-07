package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID=0;
    private static final String PRIMARY_CHANNEL_ID="primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        ToggleButton alarmToggle=findViewById(R.id.alarmToggle);

        Intent notifyIntent = new Intent(this,MainActivity.class);

        boolean alarmUp =(PendingIntent.getBroadcast(this,NOTIFICATION_ID,notifyIntent,PendingIntent.FLAG_NO_CREATE) !=null);

        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                String toastMessage;

                if (isChecked){
                    long repeatInterval =AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime = SystemClock.elapsedRealtime()+repeatInterval;
                    if (alarmManager !=null){
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,repeatInterval,notifyPendingIntent);
                    }
                    toastMessage="Stand Up Alarm ON";

                }
              else{
                    mNotificationManager.cancelAll();
                    if (alarmManager !=null){

                        alarmManager.cancel(notifyPendingIntent);
                    }
                    toastMessage="Stand Up Alarm OFF";
                }
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        createNotificationChannel();


    }
    public void createNotificationChannel(){
        mNotificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Stand Up Notification",NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(android.R.color.holo_red_light);

            notificationChannel.enableVibration(true);

            notificationChannel.setDescription("Notify every 15 minutes to Stand and walk");

            mNotificationManager.createNotificationChannel(notificationChannel);

        }

    }

}
package com.example.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID=0;
    private JobScheduler mScheduler;

    NotificationManager mNotificationManager;
    private static  final String PRIMARY_CHANNEL_ID="primary_notification_channel";

    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mSeekBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceChargingSwitch=findViewById(R.id.chargingSwitch);
        mDeviceIdleSwitch=findViewById(R.id.idleSwitch);
        mSeekBar=findViewById(R.id.seekBar);
        final TextView seekBarProgress=findViewById(R.id.seekBarProgress);

        mScheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i>0){
                    seekBarProgress.setText(getString(R.string.not_set,i));

                }else {
                    seekBarProgress.setText(getString(R.string.not_set));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void scheduleJobs(View view) {

        RadioGroup networkOptions= findViewById(R.id.networkOptions);

        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        switch (selectedNetworkID){
            case R.id.noNetwork:
                selectedNetworkOption =JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        ComponentName serviceName = new ComponentName(getPackageName(),NotificationJobService.class.getName());

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,serviceName)
                .setRequiredNetwork(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                .setRequiresCharging(mDeviceChargingSwitch.isChecked());


        boolean constrainSet = selectedNetworkOption !=JobInfo.NETWORK_TYPE_NONE||mDeviceChargingSwitch.isChecked()||mDeviceIdleSwitch.isChecked();

        if (constrainSet){

            JobInfo mJobInfo = builder.build();

            mScheduler.schedule(mJobInfo);

            Toast.makeText(this,"Job Schedule,Jobs Will Run When"+"The Constrains are met",Toast.LENGTH_SHORT).show();
        }
        else
            {
            Toast.makeText(this,"Please set atleast one constrain",Toast.LENGTH_SHORT).show();
        }

    }

    public void cancelJobs(View view) {
        if (mScheduler !=null){
            mScheduler.cancelAll();
            mScheduler=null;
            Toast.makeText(this,"Jobs Cancelled",Toast.LENGTH_SHORT).show();
        }
    }
}
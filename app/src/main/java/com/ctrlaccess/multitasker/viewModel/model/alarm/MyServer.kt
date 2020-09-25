package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ctrlaccess.multitasker.viewModel.MultitaskerViewModel


class MyService : Service() {
    private val TAG = "TAG"

    private lateinit var multitaskerViewModel: MultitaskerViewModel
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {

        multitaskerViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
                .create(MultitaskerViewModel::class.java)

//        multitaskerViewModel = ViewModelProvider(ViewModelProvider.NewInstanceFactory()).get(MultitaskerViewModel::class.java)

        Log.d(TAG, "Service: onCreate() ... ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.extras?.get(AlarmBroadcastReceiver.ReStaart_ALARM_KEY)
                ?.equals(AlarmBroadcastReceiver.ReStart_Alarm_Val)!!
        ) {
            Log.d(TAG, "service: onStartCommand() ... ")
            multitaskerViewModel.getAllAlarmsInDatabase().forEach { alarm ->
                if(alarm.repeatMode !=0){
                    MyAlarmManager(this).setRepeatingAlarm(alarm)
                }
            }
         }

        stopSelf()
        return START_STICKY //super.onStartCommand(intent, flags, startId)
    }


}

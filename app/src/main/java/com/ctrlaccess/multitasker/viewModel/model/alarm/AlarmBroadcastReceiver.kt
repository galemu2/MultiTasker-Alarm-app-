package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.viewModel.MultitaskerViewModel

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ALARM", "Ring .. Ring .. Ring .. Ring ..")

        // todo must use service or something to update next alarm
        // debug use only
        if (context != null) {
            MainActivity.multitaskViewModel
                .getAllAlarmsInDatabase().forEach { alarm ->
                    MyAlarmManager(context).serRepeatingAlarm(alarm)
                }

        }

    }
/*
*
* var weekDay:IntArray = IntArray(7) { -1}
*
* */
}

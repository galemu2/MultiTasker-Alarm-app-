package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.ctrlaccess.multitasker.MainActivity

class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val bootCompleted = "android.intent.action.BOOT_COMPLETED"
    private val TAG = "TAG"

    companion object {
        val ReStaart_ALARM_KEY = "My_alarm_is_great"
        val ReStart_Alarm_Val = "com.ctrlaccess.multitasker.Start_Alarm"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action.equals(bootCompleted)) {
            val serviceIntent = Intent(context, MyService::class.java)
            context?.startService(serviceIntent)
        } else {
            Toast.makeText(context, "Ring ring ring", Toast.LENGTH_LONG).show()

        }


        if (intent?.extras?.get(MyAlarmManager.AlarmKey)?.equals(MyAlarmManager.ThisAlarm)!!) {
            Log.d("TAG", "Ring .. Ring .. Ring .. Ring ..")

            if (context != null) {
                val serviceIntent = Intent(context, MyService::class.java)
                serviceIntent.putExtra(ReStaart_ALARM_KEY, ReStart_Alarm_Val).also { intent ->
                    context.startService(intent)
                }

            }
        }

        // todo must use service or something to update next alarm
        // debug use only
/*        if (context != null) {
            MainActivity.multitaskViewModel
                .getAllAlarmsInDatabase().forEach { alarm ->
                    if (alarm.repeatMode != 0)
                        MyAlarmManager(context).serRepeatingAlarm(alarm)
                }

        }*/

    }
/*
*
* var weekDay:IntArray = IntArray(7) { -1}
*
* */
}

package com.ctrlaccess.multitasker.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
       Log.d("ALARM", "Ring .. Ring .. Ring .. Ring ..")
    }
/*
*
* var weekDay:IntArray = IntArray(7) { -1}
*
* */
}

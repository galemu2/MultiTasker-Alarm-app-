package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import java.util.*

class MyAlarmManager(context: Context) {

    companion object {
        val AlarmKey = "alarm key"
        val ThisAlarm = "this is an alarm"
    }

    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var alarmIntent: PendingIntent =
        Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
            intent.putExtra(AlarmKey, ThisAlarm)
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

    private val TAG = "TAG"


    private fun getInterval(alarm: Alarm): Long {
        val rightNow = Calendar.getInstance()
        val alarmDay =
            alarm.calDate?.get(Calendar.DAY_OF_WEEK) ?: rightNow.get(Calendar.DAY_OF_WEEK)

        // if repeat mode is single
        if (alarm.repeatMode == 0) {
            // set alarm to next day, if alarm time is in the past
            if (alarm.calDate?.timeInMillis ?: rightNow.timeInMillis < rightNow.timeInMillis) {
                alarm.calDate?.add(Calendar.DAY_OF_WEEK, 1)
            }
            return alarm.calDate?.timeInMillis ?: rightNow.timeInMillis
        }






        if ((alarm.calDate?.timeInMillis ?: rightNow.timeInMillis) >= rightNow.timeInMillis) {

            val alarmDaySelected = alarm.days.getThisDay(alarmDay)

            // alarm day is not selected
            if (!alarmDaySelected) {

                //there are no more selected days this week
                var noMoreDays = true

                for ((index, b) in alarm.days.checkedDays.withIndex()) {
                    val idx = index + 1
                    if (b and (idx > alarmDay)) {
                        // alarm is set to next selected day
                        alarm.calDate?.apply {
                            set(Calendar.DAY_OF_WEEK, idx)

                        }
                        noMoreDays = false
                        break
                    }
                }

                if (noMoreDays) {
                    // set to next weekly, biweekly, or monthly first date
                    val firstDaySelected = alarm.daysSelected()[0]
                    var daysAdded = 0
                    val dayToday = rightNow.get(Calendar.DAY_OF_WEEK)
                    val deduct = kotlin.math.abs(dayToday - firstDaySelected)

                    if (alarm.repeatMode == 1) {
                        daysAdded = kotlin.math.abs(7 - deduct)
                    } else if (alarm.repeatMode == 2) {
                        daysAdded = kotlin.math.abs(14 - deduct)
                    } else if (alarm.repeatMode == 3) {
                        daysAdded = kotlin.math.abs(28 - deduct)
                    }

                    alarm.calDate?.add(Calendar.DAY_OF_WEEK, daysAdded)
                }
            } else {
                Log.d(TAG, "alarm day is selected: $alarmDay")
            }


        } else {
             var noMoreDays = true

            for ((index, b) in alarm.days.checkedDays.withIndex()) {
                val idx = index + 1
                if (b and (idx > alarmDay)) {
                    alarm.calDate?.apply {
                        set(Calendar.DAY_OF_WEEK, idx)
                    }
                    noMoreDays = false
                    break
                }

            }

            if (noMoreDays) {
                // set to next weekly, biweekly, or monthly first date
                val firstDaySelected = alarm.daysSelected()[0]
                var daysAdded = 0
                val dayToday = rightNow.get(Calendar.DAY_OF_WEEK)
                val deduct = kotlin.math.abs(dayToday - firstDaySelected)

                if (alarm.repeatMode == 1) {
                    daysAdded = kotlin.math.abs(7 - deduct)
                } else if (alarm.repeatMode == 2) {
                    daysAdded = kotlin.math.abs(14 - deduct)
                } else if (alarm.repeatMode == 3) {
                    daysAdded = kotlin.math.abs(28 - deduct)
                }

                alarm.calDate?.add(Calendar.DAY_OF_WEEK, daysAdded)
            }


        }



        Log.d(
            TAG,
            "Day ${
                Date(alarm.calDate?.timeInMillis ?: rightNow.timeInMillis)
            }"
        )

        // todo debug use only
        MainActivity.multitaskViewModel.updateAlarm(alarm)

        return alarm.calDate?.timeInMillis ?: rightNow.timeInMillis
    }


    fun setRepeatingAlarm(alarm: Alarm) {

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            getInterval(alarm),
            alarmIntent
        )

    }


    // todo find a way to cancel the intent
    fun cancelRepeatingAlarm() {
    }
}
package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import java.util.*

class MyAlarmManager(context: Context) {

    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var alarmIntent: PendingIntent =
        Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    private val TAG = "ALARM"

    private fun getInterval(alarm: Alarm): Long {
        val cal = Calendar.getInstance()
        val calHr = cal.get(Calendar.HOUR_OF_DAY)
        val calMin = cal.get(Calendar.MINUTE)
        val calToday = cal.get(Calendar.DAY_OF_WEEK)

        var factor = Long.MAX_VALUE

        val weekDay = LongArray(7) { -1 }
        alarm.days.checkedDays.forEachIndexed { index, checked ->
            if (checked) {

                var diff = index - (calToday - 1)

                if (diff == 0) {
                    val now = cal.timeInMillis // convertToMinute(calHr, calMin)
                    val schTime = alarm.date?.timeInMillis
                        ?: now  // convertToMinute(alarm.time.hr, alarm.time.min)
                    if (now > schTime) {
                        diff = 7
                    }
                } else if (diff < 0)
                    diff += 7

                weekDay[index] = diff.toLong()
            }
        }

        weekDay.forEach { f ->
            if (f >= 0) {
                if (f < factor)
                    factor = f
            }
        }

        if (factor == Long.MAX_VALUE) {
            factor = 0
        }
        Log.d(TAG, Arrays.toString(weekDay))
        Log.d(TAG, "factor $factor")
        return factor
    }

    private fun setTriggerTime(hr: Int, min: Int): Calendar {

        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hr)
            set(Calendar.MINUTE, min)
        }
    }

    fun serRepeatingAlarm(alarm: Alarm): PendingIntent {
        val hr = alarm.date?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY) // alarm.time.hr
        val min = alarm.date?.get(Calendar.MINUTE) ?: Calendar.getInstance()
            .get(Calendar.MINUTE)// alarm.time.min

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            setTriggerTime(hr, min).timeInMillis + (AlarmManager.INTERVAL_DAY * getInterval(
                alarm
            )),
            alarmIntent
        )

        return alarmIntent
    }


    // todo find a way to cancel the intent
    fun cancelRepeatingAlarm(p: PendingIntent?) {
        this.alarmManager.cancel(this.alarmIntent)
    }
}
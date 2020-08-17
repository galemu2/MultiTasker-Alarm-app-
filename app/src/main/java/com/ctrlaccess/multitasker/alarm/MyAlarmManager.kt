package com.ctrlaccess.multitasker.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
                    val now = convertToMinute(calHr, calMin)
                    val schTime = convertToMinute(alarm.time.hr, alarm.time.min)
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
//        Log.d(TAG, Arrays.toString(weekDay))
//        Log.d(TAG, "factor $factor")
        return factor
    }

    private fun setTriggerTime(hr: Int, min: Int): Calendar {

        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hr)
            set(Calendar.MINUTE, min)
        }
    }

    fun serRepeatingAlarm(alarm: Alarm): AlarmManager {
        val hr = alarm.time.hr
        val min = alarm.time.min

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            setTriggerTime(hr, min).timeInMillis + (AlarmManager.INTERVAL_DAY * getInterval(
                alarm
            )),
            alarmIntent
        )

        return alarmManager
    }

    private fun convertToMinute(hr: Int, min: Int): Int {
        return (hr * 60) + min
    }

    fun cancelRepeatingAlarm(alarmManager: AlarmManager?) {
        alarmManager?.cancel(this.alarmIntent)
    }
}
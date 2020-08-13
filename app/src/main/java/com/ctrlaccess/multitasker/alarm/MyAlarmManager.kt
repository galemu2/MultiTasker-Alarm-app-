package com.ctrlaccess.multitasker.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import java.util.*

class MyAlarmManager(context: Context) {

    private var alarmManager: AlarmManager? = null
    private var alarmIntent: PendingIntent

//    private val text = DaysOfWeek()
//    private val daysOfWeek_Map =
//        mapOf(
//            1 to "sun",
//            2 to "mon",
//            3 to "tue",
//            4 to "wed",
//            5 to "thurs",
//            6 to "fri",
//            7 to "sat"
//        )

    init {
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    }

    private fun getInterval(alarm: Alarm): Long {
        val cal = Calendar.getInstance()
//        val hr = cal.get(Calendar.HOUR_OF_DAY)
//        val min = cal.get(Calendar.MINUTE)
        val today = cal.get(Calendar.DAY_OF_WEEK)

        var factor = Long.MAX_VALUE

        var weekDay = LongArray(7) { -1 }
        alarm.days.checkedDays.forEachIndexed { index, checked ->
            if (checked) {
                var diff = index - (today - 1)
                if (diff == 0) {
                    //todo do some analysis for same day
                    val now = cal.timeInMillis
                    val schTime = ((alarm.time.hr * 60) + alarm.time.min).toLong()
                    if (now < schTime) {
                        diff = 7
                    }

                    if (diff < 0)
                        diff += 7

                    weekDay[index] = diff.toLong()

                }
            }
        }
        Log.d("ALARM", "factor $factor")

        weekDay.forEach { f ->
            if (f >= 0) {
                if (f < factor)
                    factor = f
            }
        }
        Log.d("ALARM", Arrays.toString(weekDay))
        Log.d("ALARM", "factor $factor")
        return factor
    }

    fun setTriggerTime(hr: Int, min: Int): Calendar {

        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hr)
            set(Calendar.MINUTE, min)
        }
    }

    fun serRepeatingAlarm(alarm: Alarm) {
        val hr = alarm.time.hr
        val min = alarm.time.min

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            setTriggerTime(hr, min).timeInMillis + (AlarmManager.INTERVAL_DAY * getInterval(
                alarm
            )),
            alarmIntent
        )


    }
}
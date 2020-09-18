package com.ctrlaccess.multitasker.viewModel.model.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import java.lang.Math.abs
import java.util.*

class MyAlarmManager(context: Context) {

    private var alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var alarmIntent: PendingIntent =
        Intent(context, AlarmBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

    private val TAG = "TAG"


    private fun getInterval(alarm: Alarm): Long {
        val rightNow = Calendar.getInstance()
        val rightNowDay = rightNow.get(Calendar.DAY_OF_WEEK)

        val alarmDateTime = alarm.calDate?.timeInMillis ?: rightNow.timeInMillis

        Log.d(TAG, "alarm time:\t ${Date(alarmDateTime)}")
        Log.d(TAG, "current time:\t ${Date()}")

        // selected days of the week
        val daysThisWeek = alarm.daysSelected()

        // past = alarm time is in the past, current time is greater or equal to alarm time
        if (rightNow.timeInMillis >= alarmDateTime) {

            Log.d(TAG, "alarm is in the past")
            var foundFutureDay = false

            if ((alarm.repeatMode > 0) and (daysThisWeek.size > 0)) {
                daysThisWeek.forEach { day ->
                    // update calendar, if there are future days
                    if (day > rightNowDay) {
                        val added = day - rightNowDay

                        // updated alarm calendar

                        alarm.calDate?.add(Calendar.DAY_OF_WEEK, added)
                        foundFutureDay = true
                        return@forEach
                    }
                }

                // there are no future days
                if (!foundFutureDay) {

                    val firstDay = daysThisWeek[0] + 1
                    var added = 0
                    // weekly repeat
                    if (alarm.repeatMode == 1) {
                        added = 7 - (rightNowDay - firstDay)

                        // biweekly repeat
                    } else if (alarm.repeatMode == 2) {
                        added = 14 - (rightNowDay - firstDay)

                        // monthly repeat
                    } else if (alarm.repeatMode == 3) {
                        added = 28 - (rightNowDay - firstDay)
                    }

                    alarm.calDate?.add(Calendar.DAY_OF_WEEK, added)

                }
            } else {
                // if alarm time is set in the past and no days are selected
                // set alarm for the following day
                if (alarm.daysSelected().size == 0) {
                    alarm.days.getThisDay(rightNowDay)
                }


                alarm.calDate?.add(Calendar.DAY_OF_WEEK, 1)
            }
        } else {
            Log.d(TAG, "Alarm is in the future")

            if ((alarm.repeatMode > 0) and (daysThisWeek.size > 0)) {

                // day of alarm
                val alarmDay = alarm.calDate?.get(Calendar.DAY_OF_WEEK) ?: 1

                var alarmDayIsSelected = false

                for ((index, day) in daysThisWeek.withIndex()) {
                    if (alarmDay == day) {
                        Log.d(TAG, "alarm day: $day and $alarmDay")
                        val n = daysThisWeek.getOrNull(index + 1)
                        if (n != null) {
                            val added = abs(alarmDay - n)
                            alarm.calDate?.add(Calendar.DAY_OF_WEEK, added)
                        } else {
                            alarmDayIsSelected = true
                            break
                        }
                        alarmDayIsSelected = false
                        break
                    } else if (day > alarmDay) {
                        Log.d(TAG, "Again, alarm day: $day and $alarmDay")

                        alarmDayIsSelected = true
                        val added = abs(day - alarmDay)
                        alarm.calDate?.add(Calendar.DAY_OF_WEEK, added)
                        break
                    }
                }


                if (alarmDayIsSelected) {
                    val firstDay = daysThisWeek[0]
                    var added = 0
                    // weekly repeat
                    if (alarm.repeatMode == 1) {
                        added = 7 - (rightNowDay - firstDay)

                        // biweekly repeat
                    } else if (alarm.repeatMode == 2) {
                        added = 14 - kotlin.math.abs(rightNowDay - firstDay)

                        // monthly repeat
                    } else if (alarm.repeatMode == 3) {
                        added = 28 - kotlin.math.abs(rightNowDay - firstDay)
                    }

                    alarm.calDate?.add(Calendar.DAY_OF_WEEK, added)
                }
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


    fun serRepeatingAlarm(alarm: Alarm) {

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
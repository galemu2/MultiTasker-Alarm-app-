package com.ctrlaccess.multitasker.viewModel.entities

import android.app.PendingIntent
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ctrlaccess.multitasker.MainActivity
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var date: Calendar?,

    @Embedded
    val days: DaysOfWeek = DaysOfWeek(),

//    var repeat: Repeat = Repeat.SINGLE,

    var alarmNote: String? = null,

    var isOn: Boolean = true,

    var scheduleListId: Long? = null

) {

    @Ignore
    var pendingIntent: PendingIntent? = null

    companion object {

        fun alarmsOnOff(alarms: List<Alarm>, scheduleIsOn: Boolean) {
            alarms.forEach { alarm ->
                alarm.isOn = scheduleIsOn
                if (!scheduleIsOn and (alarm.pendingIntent != null)) {
                    MainActivity.myAlarmManager.cancelRepeatingAlarm(alarm.pendingIntent)
                    alarm.pendingIntent = null
                } else {
                    MainActivity.myAlarmManager.serRepeatingAlarm(alarm)
                }
            }
        }

        fun alarmsAddScheduleId(alarms: List<Alarm>, scheduleListId: Long): List<Alarm> {
            alarms.forEach { alarm ->
                if (alarm.scheduleListId != scheduleListId) {
                    alarm.scheduleListId = scheduleListId
                }
            }
            return alarms
        }

        fun dateFormat(calendar: Calendar):String {

            val s = SimpleDateFormat("EEE MMM dd, YYYY")
return  s.format(Date(calendar.timeInMillis))

        }
    }
}
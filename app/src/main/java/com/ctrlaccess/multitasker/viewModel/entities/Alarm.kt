package com.ctrlaccess.multitasker.viewModel.entities

import android.app.PendingIntent
import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ctrlaccess.multitasker.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var calDate: Calendar?,
    @Embedded
    val days: DaysOfWeek = DaysOfWeek(),
    var alarmNote: String? = null,
    var isOn: Boolean = true,
    var scheduleListId: Long? = null,
    var repeatMode: Int = 0
) {
//    @Ignore
//    var pendingIntent: PendingIntent? = null

    @Ignore
    private val TAG = "TAG"

    companion object {
        fun alarmsOnOff(alarms: List<Alarm>, scheduleIsOn: Boolean) {
            alarms.forEach { alarm ->
                alarm.isOn = scheduleIsOn
                if (!scheduleIsOn) {
                    // todo cancel current alarm is alarm is off
                } else {
                    //MainActivity.myAlarmManager.serRepeatingAlarm(alarm)
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

        fun dateFormat(calendar: Calendar): String {
            val s = SimpleDateFormat("EEE MMM dd, YYYY")
            return s.format(Date(calendar.timeInMillis))
        }

    }

    fun daysSelected(): ArrayList<Int> {
        val selectedDays = arrayListOf<Int>()
        this.days.checkedDays.forEachIndexed { index, selected ->
            if (selected) {
                selectedDays.add(index + 1)
            }
        }
        Log.d(TAG, "selected days: $selectedDays")
        return selectedDays
    }
}
package com.ctrlaccess.multitasker.viewModel.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @Embedded
    var time: AlarmTime,

    @Embedded
    val days: DaysOfWeek = DaysOfWeek(),

    var alarmNote: String? = null,

    var isOn: Boolean = true,

    var scheduleListId: Long? = null
) {
    companion object {
        fun alarmsOnOff(alarms: List<Alarm>, scheduleIsOn: Boolean) {

            alarms.forEach { alarm ->
                alarm.isOn = scheduleIsOn
            }
        }

        fun alarmsAddScheduleId(alarms: List<Alarm>, scheduleListId: Long):List<Alarm>{

            alarms.forEach { alarm ->
                if(alarm.scheduleListId != scheduleListId){
                    alarm.scheduleListId = scheduleListId
                }
            }
            return alarms
        }
    }
}
package com.ctrlaccess.multitasker.database.entities

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
    val days: DaysOfWeek  = DaysOfWeek(),

    var alarmNote: String? = null,

    var scheduleListId: Long? = null
)
package com.ctrlaccess.multitasker.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @Embedded
    val time: AlarmTime,

    @Embedded
    val days: DaysOfWeek? = null,

    val alarmNote: String? = null,

    val scheduleListId: Long? = null
)
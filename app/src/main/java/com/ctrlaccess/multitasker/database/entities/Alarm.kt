package com.ctrlaccess.multitasker.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Embedded val time: AlarmTime,
    @Embedded val days: DaysOfWeek,
    val alarmNote: String,
    val scheduleListId: Long
)
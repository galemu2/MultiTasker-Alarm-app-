package com.ctrlaccess.multitasker.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) val scheduleId: Long,
    val schedule: String,
    val scheduleNote: String?
)
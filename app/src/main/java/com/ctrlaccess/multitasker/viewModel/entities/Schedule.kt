package com.ctrlaccess.multitasker.viewModel.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long = 0L,
    var schedule: String,
    var scheduleNote: String?,
    var numberOfAlarms:Int = 0,
    var isOn:Boolean = true
)
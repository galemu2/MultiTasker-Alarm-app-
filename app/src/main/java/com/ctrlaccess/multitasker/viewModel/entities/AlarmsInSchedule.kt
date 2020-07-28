package com.ctrlaccess.multitasker.viewModel.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AlarmsInSchedule(
    @Embedded val scheduleList: Schedule,
    @Relation(
        parentColumn = "scheduleId",
        entityColumn = "scheduleListId"
    )
    val alarms: List<Alarm>
)
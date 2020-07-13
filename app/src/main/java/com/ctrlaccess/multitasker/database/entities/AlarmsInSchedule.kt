package com.ctrlaccess.multitasker.database.entities

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
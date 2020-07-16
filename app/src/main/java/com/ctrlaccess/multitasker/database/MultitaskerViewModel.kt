package com.ctrlaccess.multitasker.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ctrlaccess.multitasker.database.entities.Alarm

class MultitaskerViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmsRepository: AlarmsRepository
    private val scheduleRepository: ScheduleRepository
    private val multitaskerRepository: MultitaskerRepository


    init {
        val db = MultitaskerDatabase.getDatabase(application)
        val alarmDao = db.alarmDao()
        alarmsRepository = AlarmsRepository(alarmDao)

        val scheduleDao = db.scheduleDao()
        scheduleRepository = ScheduleRepository(scheduleDao)

        val multitaskerDao = db.multitaskerDao()
        multitaskerRepository = MultitaskerRepository(multitaskerDao)
    }


    fun insertAlarm(alarms: List<Alarm>) {
        alarmsRepository.insertAlarms(alarms)
    }
}
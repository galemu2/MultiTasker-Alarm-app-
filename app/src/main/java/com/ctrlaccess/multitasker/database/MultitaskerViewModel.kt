package com.ctrlaccess.multitasker.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MultitaskerViewModel(application: Application) : AndroidViewModel(application) {

    private val alarmsRepository: AlarmsRepository
    private val scheduleRepository: ScheduleRepository
    private val multitaskerRepository: MultitaskerRepository

    val allSchedules: LiveData<List<Schedule>>

    init {
        val db = MultitaskerDatabase.getDatabase(application)
        val alarmDao = db.alarmDao()
        alarmsRepository = AlarmsRepository(alarmDao)

        val scheduleDao = db.scheduleDao()
        scheduleRepository = ScheduleRepository(scheduleDao)

        val multitaskerDao = db.multitaskerDao()
        multitaskerRepository = MultitaskerRepository(multitaskerDao)

        allSchedules = scheduleRepository.allSchedules
    }

    fun insertAlarm(alarms: Alarm): Long {
        return alarmsRepository.insertAlarm(alarms)
    }

    fun updateAlarm(alarms: Alarm) {
        alarmsRepository.updateAlarm(alarms)
    }

    fun deleteAlarm(alarms: Alarm) {
        alarmsRepository.deleteAlarm(alarms)
    }

    fun insertAlarms(alarms: List<Alarm>): List<Long> {
        var out = emptyList<Long>()
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                out = alarmsRepository.insertAlarms(alarms)
            }
        }
        return out
    }

    fun insertSchedule(schedule: Schedule): Long {
        var insertedId = 0L
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                insertedId = scheduleRepository.insertSchedule(schedule)
                // viewModelScope.launch(Dispatchers.IO) { }
            }
        }
        return insertedId
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                scheduleRepository.deleteSchedule(schedule)
            }
        }
    }
}
package com.ctrlaccess.multitasker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import com.ctrlaccess.multitasker.viewModel.entities.Schedule
import com.ctrlaccess.multitasker.viewModel.model.AlarmsRepository
import com.ctrlaccess.multitasker.viewModel.model.MultitaskerDatabase
import com.ctrlaccess.multitasker.viewModel.model.MultitaskerRepository
import com.ctrlaccess.multitasker.viewModel.model.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MultitaskerViewModel(application: Application) :
    AndroidViewModel(application) {

    private val alarmsRepository: AlarmsRepository
    private val scheduleRepository: ScheduleRepository
    private val multitaskerRepository: MultitaskerRepository
    private val TAG = "TAG"
    val allSchedules: LiveData<List<Schedule>>

    init {
        val db = MultitaskerDatabase.getDatabase(application)
        val alarmDao = db.alarmDao()
        alarmsRepository =
            AlarmsRepository(alarmDao)

        val scheduleDao = db.scheduleDao()
        scheduleRepository =
            ScheduleRepository(
                scheduleDao
            )

        val multitaskerDao = db.multitaskerDao()
        multitaskerRepository =
            MultitaskerRepository(
                multitaskerDao
            )

        allSchedules = scheduleRepository.allSchedules
    }

    fun insertAlarm(alarm: Alarm): Long {
        var out = -1L
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                out = alarmsRepository.insertAlarm(alarm)
            }
        }
        return out
    }

    fun updateAlarm(alarms: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.updateAlarm(alarms)
         }
    }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.deleteAlarm(alarm)
        }

    }

    fun updateAlarms(alarms: List<Alarm>) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.updateAlarm(alarms)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            scheduleRepository.updateSchedule(schedule)
        }
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

    fun getAllAlarms(scheduleId: Long): List<Alarm> {
        var out = emptyList<Alarm>()
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                out = alarmsRepository.getScheduleAlarms(scheduleId)
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
        viewModelScope.launch(Dispatchers.IO) {
            scheduleRepository.deleteSchedule(schedule)
        }
    }

    fun deleteAllAlarms(scheduleListId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.deleteAllAlarms(scheduleListId)
        }
    }

    fun getAllSchedulesChecker(): List<Schedule> {
        var outList = emptyList<Schedule>()
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                outList = scheduleRepository.getAllSchedulesChecer()
            }
        }

        return outList
    }

    fun getAllAlarmsInDatabase(): List<Alarm> {
        var outAlarms = emptyList<Alarm>()
        viewModelScope.run {
            runBlocking(Dispatchers.IO) {
                outAlarms = alarmsRepository.getAllAlarmsInDatabase()
            }
        }
        return outAlarms
    }
}
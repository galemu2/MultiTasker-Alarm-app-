package com.ctrlaccess.multitasker.database

import androidx.lifecycle.LiveData
import com.ctrlaccess.multitasker.database.dao.AlarmDao
import com.ctrlaccess.multitasker.database.dao.MultitaskerDao
import com.ctrlaccess.multitasker.database.dao.ScheduleDao
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.AlarmsInSchedule
import com.ctrlaccess.multitasker.database.entities.Schedule

class MultitaskerRepository(private val multitaskerDao: MultitaskerDao) {
    val getAlarmsInSchedule: LiveData<List<AlarmsInSchedule>> = multitaskerDao.getAlarmsInSchedule()
}

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    fun deleteSchedule(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule)

    }

    fun insertSchedule(schedule: Schedule) {
        scheduleDao.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }
}

class AlarmsRepository(private val alarmDao: AlarmDao) {

    fun getAlarms(scheduleListId: Long): LiveData<List<Alarm>> {
        return alarmDao.getAlarms(scheduleListId)
    }

    fun insertAlarms(alarms: List<Alarm>) {
        alarmDao.insertAlarms(alarms)
    }

    fun updateAlarm(alarms: List<Alarm>) {
        alarmDao.updateAlarms(alarms)
    }

    fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm)
    }

    fun deleteAllAlarms(scheduleListId: Long) {
        alarmDao.deleteAlarms(scheduleListId)
    }
}
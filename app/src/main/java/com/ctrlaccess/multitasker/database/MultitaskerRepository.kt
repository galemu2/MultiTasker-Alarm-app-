package com.ctrlaccess.multitasker.database

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.ctrlaccess.multitasker.database.dao.AlarmDao
import com.ctrlaccess.multitasker.database.dao.MultitaskerDao
import com.ctrlaccess.multitasker.database.dao.ScheduleDao
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.AlarmsInSchedule
import com.ctrlaccess.multitasker.database.entities.Schedule

class MultitaskerRepository(private val multitaskerDao: MultitaskerDao) {
//    val getAlarmsInSchedule: LiveData<List<AlarmsInSchedule>> = multitaskerDao.getAlarmsInSchedule()
}

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    val allSchedules: LiveData<List<Schedule>> = scheduleDao.getAllSchedules()

    fun deleteSchedule(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule)

    }

    fun insertSchedule(schedule: Schedule): Long {
        return scheduleDao.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }

    // used for development purposes only
    fun getAllSchedulesChecer(): List<Schedule> {
        return scheduleDao.getAllSchedulesChecker()
    }
}

class AlarmsRepository(private val alarmDao: AlarmDao) {

    /*
    fun getAlarms(scheduleListId: Long): LiveData<List<Alarm>> {
            return alarmDao.getAlarms(scheduleListId)
        }
    */
    fun insertAlarm(alarm: Alarm): Long {
        /*
        var res: Long = -1L

        // MultitaskerDatabase.executor.awaitTermination()
        MultitaskerDatabase.executor.execute {
            res = alarmDao.insertAlarm(alarm)
        }
        */

        return alarmDao.insertAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) {
        alarmDao.updateAlarm(alarm)
    }

    fun getScheduleAlarms(scheduleListId: Long): List<Alarm> {
        return alarmDao.getAlarms(scheduleListId)
    }

    // insert a list of alarms todo may not be necessary
    fun insertAlarms(alarms: List<Alarm>): List<Long> {
        return alarmDao.insertAlarms(alarms)
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

    // used for developement purposes only
    fun getAllAlarmsChecker(): List<Alarm> {
        return alarmDao.getAllAlarmsChecker()
    }
}

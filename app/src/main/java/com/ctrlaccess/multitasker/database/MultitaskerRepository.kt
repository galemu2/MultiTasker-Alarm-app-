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

    /*
    fun getAlarms(scheduleListId: Long): LiveData<List<Alarm>> {
            return alarmDao.getAlarms(scheduleListId)
        }
    */
    fun insertAlarm(alarm: Alarm): Long {
        var res: Long = -1L

//        MultitaskerDatabase.executor.awaitTermination()
        MultitaskerDatabase.executor.execute {
            res = alarmDao.insertAlarm(alarm)
        }

/*        while (res < 0){
            Log.d("TAG", "res: $res")
        }*/

        return res
    }

    fun updateAlarm(alarm: Alarm) {
        alarmDao.updateAlarm(alarm)
    }

    fun getScheduleAlarms(scheduleListId: Long): LiveData<List<Alarm>> {
        return alarmDao.getAlarms(scheduleListId)
    }

    // insert a list of alarms todo may not be necessary
    fun insertAlarms(alarms: List<Alarm>): List<Long> {
        var res = emptyList<Long>()
        MultitaskerDatabase.executor.execute {
            res = alarmDao.insertAlarms(alarms)
        }
        return res
    }

    fun updateAlarm(alarms: List<Alarm>) {
        alarmDao.updateAlarms(alarms)
    }

    fun deleteAlarm(alarm: Alarm) {
        alarmDao.deleteAlarm(alarm)
    }

    suspend fun deleteAllAlarms(scheduleListId: Long) {
        alarmDao.deleteAlarms(scheduleListId)
    }
}

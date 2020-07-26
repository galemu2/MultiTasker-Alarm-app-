package com.ctrlaccess.multitasker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ctrlaccess.multitasker.database.entities.Alarm

@Dao
interface AlarmDao {
    // insert a single alarm
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: Alarm): Long

    @Update
    fun updateAlarm(alarm: Alarm)

    // delete a single alarm
    @Delete
    fun deleteAlarm(alarm: Alarm)

    // update alarms
    @Update
    fun updateAlarms(alarm: List<Alarm>)

    // query all alarms with a specific scheduleListId todo may note be necessary
    @Query("SELECT * FROM Alarm WHERE scheduleListId= :scheduleId")
    fun getAlarms(scheduleId: Long):  List<Alarm>

    // delete alarms associated with a schedule
    @Query("Delete from Alarm where scheduleListId=:scheduleListId")
    fun deleteAlarms(scheduleListId: Long)

    // inserting the list of alarms TODO should find a way to add "scheduleListId"
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlarms(alarms: List<Alarm>): List<Long>
}
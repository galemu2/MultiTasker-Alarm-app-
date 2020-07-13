package com.ctrlaccess.multitasker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ctrlaccess.multitasker.database.entities.Alarm

@Dao
interface AlarmDao {
    // query all alarms with a specific scheduleListId
    @Query("SELECT * FROM Alarm WHERE scheduleListId= :scheduleId")
    fun getAlarms(scheduleId: Long): LiveData<List<Alarm>>

    // inserting the list of alarms TODO should find a way to add "scheduleListId"
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarms(alarms: List<Alarm>)//: List<Long>

    // update alarms
    @Update
    fun updateAlarms(alarm: List<Alarm>)

    // delete a single alarm
    @Delete
    fun deleteAlarm(alarm: Alarm)

    // delete alarms
    @Delete
    fun deleteAlarms(scheduleListId: Long)
}
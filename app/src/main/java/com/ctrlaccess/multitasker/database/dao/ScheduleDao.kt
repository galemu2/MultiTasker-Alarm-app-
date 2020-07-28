package com.ctrlaccess.multitasker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ctrlaccess.multitasker.database.entities.Schedule

@Dao
interface ScheduleDao {

    // insert the schedule list, will return the id when inserting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(scheduleList: Schedule): Long

    //update the schedule
    @Update
    fun updateSchedule(scheduleList: Schedule)

    // delete the schedule and all the associated alarms
    @Delete
    fun deleteSchedule(scheduleList: Schedule)
    // todo all associated alarms should be deleted when deleting a schedule

    @Query("SELECT * FROM Schedule")
    fun getAllSchedules(): LiveData<List<Schedule>>

    @Query("SELECT * FROM Schedule")
    fun getAllSchedulesChecker():List<Schedule>
}
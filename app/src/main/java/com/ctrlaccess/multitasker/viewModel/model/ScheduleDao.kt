package com.ctrlaccess.multitasker.viewModel.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ctrlaccess.multitasker.viewModel.entities.Schedule

@Dao
interface ScheduleDao {

    // insert the schedule list, will return the id when inserting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(scheduleList: Schedule): Long

    //update the schedule
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSchedule(scheduleList: Schedule): Int

    // delete the schedule and all the associated alarms
    @Delete
    fun deleteSchedule(scheduleList: Schedule)
    // todo all associated alarms should be deleted when deleting a schedule

    @Query("SELECT * FROM Schedule")
    fun getAllSchedules(): LiveData<List<Schedule>>

    @Query("SELECT * FROM Schedule")
    fun getAllSchedulesChecker(): List<Schedule>
}
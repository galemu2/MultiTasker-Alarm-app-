package com.ctrlaccess.multitasker.database.dao

import androidx.room.*
import com.ctrlaccess.multitasker.database.entities.Schedule

@Dao
interface ScheduleDao {

    // insert the schedule list, will return the id when inserting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(scheduleList: Schedule) //: Long

    //update the schedule
    @Update
    fun updateSchedule(scheduleList: Schedule)

    // delete the schedule and all the associated alarms
    @Delete
    fun deleteSchedule(scheduleList: Schedule)
}
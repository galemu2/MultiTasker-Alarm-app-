package com.ctrlaccess.multitasker.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ctrlaccess.multitasker.database.entities.AlarmsInSchedule

@Dao
interface MultitaskerDao {

    // query the list of alarms for a schedule
    @Transaction
    @Query("select * from Schedule")
    fun getAlarmsInSchedule(): LiveData<List<AlarmsInSchedule>>

}




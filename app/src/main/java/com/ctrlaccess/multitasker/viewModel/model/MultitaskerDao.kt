package com.ctrlaccess.multitasker.viewModel.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ctrlaccess.multitasker.viewModel.entities.AlarmsInSchedule

@Dao
interface MultitaskerDao {

    // query the list of alarms for a schedule
    @Transaction
    @Query("select * from Schedule")
    fun getAlarmsInSchedule():   List<AlarmsInSchedule>

}




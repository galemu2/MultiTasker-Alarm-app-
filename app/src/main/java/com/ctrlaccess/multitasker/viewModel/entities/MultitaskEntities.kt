package com.ctrlaccess.multitasker.viewModel.entities

import androidx.room.Ignore

data class DaysOfWeek(
    var sun: Boolean = false,
    var mon: Boolean = false,
    var tue: Boolean = false,
    var wed: Boolean = false,
    var thurs: Boolean = false,
    var fri: Boolean = false,
    var sat: Boolean = false
) {
//    @Ignore
//    var weekDay = LongArray(7) { -1 }

    @Ignore
    val checkedDays = arrayOf(sun, mon, tue, wed, thurs, fri, sat)
}

/*
*
*     var sun: Boolean = false,
    var mon: Boolean = false,
    var tue: Boolean = false,
    var wed: Boolean = false,
    var thurs: Boolean = false,
    var fri: Boolean = false,
    var sat: Boolean = false
    * */
data class AlarmTime(
    var hr: Int,
    var min: Int
)
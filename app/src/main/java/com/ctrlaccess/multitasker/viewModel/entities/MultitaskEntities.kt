package com.ctrlaccess.multitasker.viewModel.entities

import androidx.room.Ignore
import androidx.room.TypeConverter
import java.util.*

data class DaysOfWeek(
    var sun: Boolean = false,
    var mon: Boolean = false,
    var tue: Boolean = false,
    var wed: Boolean = false,
    var thurs: Boolean = false,
    var fri: Boolean = false,
    var sat: Boolean = false
) {

    // this is useless
    @Ignore
    val checkedDays = arrayOf(sun, mon, tue, wed, thurs, fri, sat)

    fun getThisDay(day: Int): Boolean {
        return when (day) {
            1 -> this.sun
            2 -> this.mon
            3 -> this.tue
            4 -> this.wed
            5 -> this.thurs
            6 -> this.fri
            7 -> this.sat
            else -> false
        }
    }
}

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return Calendar.getInstance().apply {
            timeInMillis = value ?: System.currentTimeMillis()
        }
    }

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}


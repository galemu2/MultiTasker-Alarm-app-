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
//    @Ignore
//    var weekDay = LongArray(7) { -1 }

    @Ignore
    val checkedDays = arrayOf(sun, mon, tue, wed, thurs, fri, sat)
}

//data class AlarmTime(
//    var hr: Int,
//    var min: Int
//)

class Converters {

    @TypeConverter
    fun fromTimestamp(value:Long? ):Calendar?{
        return Calendar.getInstance().apply {
            timeInMillis = value ?: System.currentTimeMillis()
        }
    }

    @TypeConverter
    fun calendarToTimestamp(calendar:Calendar?):Long?{
        return calendar?.timeInMillis
    }
}
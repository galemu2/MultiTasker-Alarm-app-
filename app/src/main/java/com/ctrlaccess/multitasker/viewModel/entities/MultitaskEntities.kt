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

    @Ignore
    val checkedDays = arrayOf(sun, mon, tue, wed, thurs, fri, sat)
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

//enum class Repeat {
//    SINGLE {
//        val single = 0
//    },
//
//    WEEKLY {
//        val weekly = 1
//    },
//
//    BIWEEKLY {
//        val bilweekly = 2
//    },
//
//    MONTHLY {
//        val monthly = 3
//    }
//}

package com.ctrlaccess.multitasker.database.entities

data class DaysOfWeek(
    val sun: Boolean,
    val mon: Boolean,
    val tue: Boolean,
    val wed: Boolean,
    val thurs: Boolean,
    val fri: Boolean,
    val sat: Boolean
)

data class AlarmTime(
    val hr: Int,
    val min: Int
)
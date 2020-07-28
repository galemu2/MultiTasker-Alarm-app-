package com.ctrlaccess.multitasker.viewModel.entities

data class DaysOfWeek(
    var sun: Boolean = false,
    var mon: Boolean = false,
    var tue: Boolean = false,
    var wed: Boolean = false,
    var thurs: Boolean = false,
    var fri: Boolean = false,
    var sat: Boolean = false
)

data class AlarmTime(
    var hr: Int,
    var min: Int
)
package com.ctrlaccess.multitasker.util

import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment1Schedules
import com.ctrlaccess.multitasker.Fragment2Alarms
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.viewModel.entities.Alarm

class ScheduleElementItemTouchCallback(
    val fragment1Schedules: Fragment1Schedules,
    dragDirs: Int = 0,
    swipeDirs: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val position = viewHolder.adapterPosition
        val schedule = Fragment1Schedules.schedules.get(position)
        val scheduleId = schedule.scheduleId

        // swipe and delete schedule and associated alarms
        val alarms = MainActivity.multitaskViewModel.getAllAlarms(scheduleId)
        // cancel alarm
        alarms.forEach { alarm ->
            MainActivity.myAlarmManager.cancelRepeatingAlarm(alarm.pendingIntent)
        }

        MainActivity.multitaskViewModel.deleteAllAlarms(scheduleId)
        MainActivity.multitaskViewModel.deleteSchedule(schedule)

        Toast.makeText(
            viewHolder.itemView.context,
            "ID: ${schedule.scheduleId}",
            Toast.LENGTH_SHORT
        ).show()

        fragment1Schedules.recyclerViewSchedulesAdaptor.notifyDataSetChanged()
    }

}
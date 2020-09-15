package com.ctrlaccess.multitasker.util

import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment2Alarms
import com.ctrlaccess.multitasker.MainActivity

class AlarmElementItemTouchCallback(
    private val recyclerView2AlarmsAdaptor: RecyclerView2AlarmsAdaptor,
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

        if (Fragment2Alarms.alarms.size > 0) {
            val alarm = Fragment2Alarms.alarms.get(position)
            val success = Fragment2Alarms.alarms.remove(alarm)

            if (success) {
                // turn off alarm
                 // delete alarm
                MainActivity.multitaskViewModel.deleteAlarm(alarm)
                Toast.makeText(
                    viewHolder.itemView.context,
                    "Alarm at position :${position + 1} removed",
                    Toast.LENGTH_SHORT
                ).show()
                recyclerView2AlarmsAdaptor.notifyDataSetChanged()
            }
        }

    }
}



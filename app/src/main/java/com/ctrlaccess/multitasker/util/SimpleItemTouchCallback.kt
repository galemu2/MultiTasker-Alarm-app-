package com.ctrlaccess.multitasker.util

import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.RecyclerView1AlarmsAdaptor

class SimpleItemTouchCallback(
    private val recyclerView1AlarmsAdaptor: RecyclerView1AlarmsAdaptor,
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
        val success = recyclerView1AlarmsAdaptor
            .deleteSwipedElement(position)
        if (success) {

            Toast.makeText(
                viewHolder.itemView.context,
                "Alarm at position :${position + 1} removed",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}


interface SwipedElement {
    fun deleteSwipedElement(position: Int): Boolean
}
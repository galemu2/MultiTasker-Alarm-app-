package com.ctrlaccess.multitasker.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment1Schedules
import com.ctrlaccess.multitasker.R
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import com.ctrlaccess.multitasker.viewModel.entities.Schedule
import kotlinx.coroutines.MainScope

class RecyclerView1SchedulesAdaptor(context: Context) :
    RecyclerView.Adapter<RecyclerView1SchedulesAdaptor.ScheduleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var schedules = emptyList<Schedule>()
    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleViewHolder {

        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.element2_lists, parent, false
        )

        return ScheduleViewHolder(
            binding.root
        )
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(
        holder: ScheduleViewHolder,
        position: Int
    ) {

        val currentSchedule = schedules[position]

        scheduleHolderBackground(currentSchedule, holder)

        holder.scheduleView.text = currentSchedule.schedule
        holder.numberOfAlarms.text = currentSchedule.numberOfAlarms.toString()

        holder.scheduleNoteView.text = currentSchedule.scheduleNote ?: ""


        holder.itemView.setOnClickListener { v ->
            currentSchedule.isOn = !currentSchedule.isOn

            val alarms =
                Fragment1Schedules.multitaskerViewModel
                    .getAllAlarms(currentSchedule.scheduleId)

            scheduleHolderBackground(currentSchedule, holder)
            Fragment1Schedules.multitaskerViewModel.updateSchedule(currentSchedule)
            Alarm.modifyAlarms(alarms, currentSchedule.isOn)
            Fragment1Schedules.multitaskerViewModel.updateAlarms(alarms)
        }

        holder.itemView.setOnLongClickListener { v ->
            Toast.makeText(v.context, "long click ...", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun scheduleHolderBackground(
        currentSchedule: Schedule,
        holder: ScheduleViewHolder
    ) {
        if (currentSchedule.isOn) {
            holder.container.setBackgroundColor(holder.container.context.resources.getColor(R.color.schedule_on))
        } else {
            holder.container.setBackgroundColor(holder.container.context.resources.getColor(R.color.schedule_off))
        }
    }

    internal fun setSchedules(schedule: List<Schedule>) {
        this.schedules = schedule
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val scheduleView: TextView = itemView.findViewById(R.id.textView2_schedule_title)
        val scheduleNoteView: TextView = itemView.findViewById(R.id.textView3_schedule_note)

        val numberOfAlarms: TextView = itemView.findViewById(R.id.textView1_number_of_alarm)

        val container: ConstraintLayout = itemView.findViewById(R.id.schedule_container)
    }
}
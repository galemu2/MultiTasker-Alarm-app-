package com.ctrlaccess.multitasker.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
 import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment1Schedules
import com.ctrlaccess.multitasker.Fragment1SchedulesDirections
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.R
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import com.ctrlaccess.multitasker.viewModel.entities.Schedule

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

        viewHolderBackground(currentSchedule, holder)

        holder.scheduleView.text = currentSchedule.schedule
        holder.numberOfAlarms.text = currentSchedule.numberOfAlarms.toString()

        holder.scheduleNoteView.text = currentSchedule.scheduleNote ?: ""

        holder.itemView.setOnClickListener { v ->
            currentSchedule.isOn = !currentSchedule.isOn

            val alarms =
                MainActivity.multitaskViewModel
                    .getAllAlarms(currentSchedule.scheduleId)

            viewHolderBackground(currentSchedule, holder)
            MainActivity.multitaskViewModel.updateSchedule(currentSchedule)
            Alarm.alarmsOnOff(alarms, currentSchedule.isOn)
            MainActivity.multitaskViewModel.updateAlarms(alarms)
        }

        holder.itemView.setOnLongClickListener { v ->

            updateDialog(
                v.context, currentSchedule.schedule,
                currentSchedule.scheduleNote,
                currentSchedule.scheduleId
            )
            true
        }
        

    }

    private fun updateDialog(
        context: Context,
        title: String,
        note: String? = null,
        scheduleId: Long  = -1
    ) {
        val bindingAlert = Fragment1Schedules.inflateAlert1Binding(context)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setView(bindingAlert.root)

        bindingAlert.editTextText1Title.setText(title)
        bindingAlert.editTextText1Note.setText(note)

        builder.setPositiveButton(R.string.edit) { dialog, which ->
            Fragment1Schedules.binding.root
                .findNavController().navigate(

                    Fragment1SchedulesDirections.actionFragmentScheduleListToFragmentAlarmList(
                        bindingAlert.editTextText1Title.text.toString(),
                        bindingAlert.editTextText1Note.text.toString(),
                        scheduleId
                    )
                )
             dialog.dismiss()
        }
        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun viewHolderBackground(
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
package com.ctrlaccess.multitasker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.entities.Schedule

class RecyclerView2SchedulesAdaptor(context: Context) :
    RecyclerView.Adapter<RecyclerView2SchedulesAdaptor.ScheduleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var schedules = emptyList<Schedule>()
    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView2SchedulesAdaptor.ScheduleViewHolder {

        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.element2_lists, parent, false
        )

        return ScheduleViewHolder(binding.root)
    }


    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView2SchedulesAdaptor.ScheduleViewHolder,
        position: Int
    ) {
        val currentSchedule = schedules[position]

        holder.scheduleView.text = currentSchedule.schedule
        holder.numberOfAlarms.text = currentSchedule.numberOfAlarms.toString()

        holder.scheduleNoteView.text = currentSchedule.scheduleNote ?: ""

    }

    internal fun setSchedules(schedule: List<Schedule>) {
        this.schedules = schedule
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val scheduleView: TextView = itemView.findViewById(R.id.textView2_schedule_title)
        val scheduleNoteView: TextView = itemView.findViewById(R.id.textView3_schedule_note)

        val numberOfAlarms: TextView = itemView.findViewById(R.id.textView1_number_of_alarm)
    }
}
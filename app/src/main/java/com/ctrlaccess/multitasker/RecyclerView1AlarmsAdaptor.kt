package com.ctrlaccess.multitasker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.entities.Alarm
import java.sql.Time
import java.text.SimpleDateFormat

class RecyclerView1AlarmsAdaptor(context: Context) :
    RecyclerView.Adapter<RecyclerView1AlarmsAdaptor.AlarmsViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<Alarm>()
    lateinit var binding: ViewDataBinding


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmsViewHolder {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.element1_alarm,
            parent,
            false
        )

//        val view = inflater.inflate(R.layout.element1_alarm, parent, false)
        return AlarmsViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(
        holder: AlarmsViewHolder,
        position: Int
    ) {
        val currentAlarm = alarms[position]
        holder.alarmView.text = getTime(currentAlarm)

    }

    internal fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    private fun clickCheckBox(checkBox: CheckBox) {
        Toast.makeText(checkBox.context, "something", Toast.LENGTH_SHORT).show()
        if (checkBox.isChecked) {
            Toast.makeText(checkBox.context, "clicked: " + checkBox.text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getTime(alarm: Alarm): String {

        val time: Time = Time(alarm.time.hr, alarm.time.min, 0)
        val formatter: SimpleDateFormat = SimpleDateFormat("hh:mm a")

        return formatter.format(time)
    }

    class AlarmsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val alarmView: TextView = itemView.findViewById(R.id.textView1_element_alarm_time)
        val noteView: EditText? = itemView.findViewById(R.id.editText2_element_alarm_note)

        val checkBoxSunday: CheckBox = itemView.findViewById(R.id.checkBoxSunday)
        val checkBoxMonday: CheckBox = itemView.findViewById(R.id.checkBoxMonday)
        val checkBoxTuesday: CheckBox = itemView.findViewById(R.id.checkBoxTuesday)
        val checkBoxWednesday: CheckBox = itemView.findViewById(R.id.checkBoxWednesday)
        val checkBoxThursday: CheckBox = itemView.findViewById(R.id.checkBoxThursday)
        val checkBoxFriday: CheckBox = itemView.findViewById(R.id.checkBoxFriday)
        val checkBoxSaturday: CheckBox = itemView.findViewById(R.id.checkBoxSaturday)


    }
}
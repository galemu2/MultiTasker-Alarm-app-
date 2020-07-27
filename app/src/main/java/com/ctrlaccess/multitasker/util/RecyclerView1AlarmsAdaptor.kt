package com.ctrlaccess.multitasker.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment2Alarms
import com.ctrlaccess.multitasker.R
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
            R.layout.element1_alarms,
            parent,
            false
        )

//        val view = inflater.inflate(R.layout.element1_alarms, parent, false)
        return AlarmsViewHolder(
            binding.root
        )
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

        holder.checkBoxSunday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.sun = !currentAlarm.days.sun
        }

        holder.checkBoxMonday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.mon = !currentAlarm.days.mon
        }
        holder.checkBoxTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.tue = !currentAlarm.days.tue
        }
        holder.checkBoxWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.wed = !currentAlarm.days.wed
        }
        holder.checkBoxThursday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.thurs = !currentAlarm.days.thurs
        }
        holder.checkBoxFriday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.fri = !currentAlarm.days.fri
        }
        holder.checkBoxSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.sat = !currentAlarm.days.sat
        }

        holder.alarmNoteView?.addTextChangedListener {
            currentAlarm.alarmNote = holder.alarmNoteView?.text.toString()
        }

        holder.itemView.setOnClickListener {
            currentAlarm.isOn = !currentAlarm.isOn

            if (currentAlarm.isOn) {
                holder.container.setBackgroundColor(holder.container.context.resources.getColor(
                    R.color.alarm_is_on
                ))
                Toast.makeText(holder.itemView.context, "Alarm is On", Toast.LENGTH_SHORT).show()
            } else {
                holder.container.setBackgroundColor(holder.container.context.resources.getColor(
                    R.color.alarm_is_off
                ))
                Toast.makeText(holder.itemView.context, "Alarm is Off", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.setOnLongClickListener {
            editAlertDialog2(it)
            true
        }

    }


    // todo will implement update alarm in database
    fun editAlertDialog2(v: View) {
        val bindingAlart =
            Fragment2Alarms.alert1Binding(v.context)

        val builder: AlertDialog.Builder = AlertDialog.Builder(v.context)

        builder.setPositiveButton(R.string.update) { dialog, whick ->
            Toast.makeText(v.context, "created", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }

        builder.setView(bindingAlart.root)
        builder.create().show()

    }

    internal fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    private fun getTime(alarm: Alarm): String {
        val time: Time = Time(alarm.time.hr, alarm.time.min, 0)
        val formatter: SimpleDateFormat = SimpleDateFormat("hh:mm a")
        return formatter.format(time)
    }


    class AlarmsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val alarmView: TextView = itemView.findViewById(R.id.textView1_element_alarm_time)
        val alarmNoteView: EditText? = itemView.findViewById(R.id.editText2_element_alarm_note)

        val checkBoxSunday: CheckBox = itemView.findViewById(R.id.checkBoxSunday)
        val checkBoxMonday: CheckBox = itemView.findViewById(R.id.checkBoxMonday)
        val checkBoxTuesday: CheckBox = itemView.findViewById(R.id.checkBoxTuesday)
        val checkBoxWednesday: CheckBox = itemView.findViewById(R.id.checkBoxWednesday)
        val checkBoxThursday: CheckBox = itemView.findViewById(R.id.checkBoxThursday)
        val checkBoxFriday: CheckBox = itemView.findViewById(R.id.checkBoxFriday)
        val checkBoxSaturday: CheckBox = itemView.findViewById(R.id.checkBoxSaturday)

        val container: ConstraintLayout = itemView.findViewById(R.id.constraint1_element_alarm)

    }


}
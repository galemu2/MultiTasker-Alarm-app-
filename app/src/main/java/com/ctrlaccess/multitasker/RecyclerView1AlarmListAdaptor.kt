package com.ctrlaccess.multitasker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerView1AlarmListAdaptor(context: Context) :
    RecyclerView.Adapter<RecyclerView1AlarmListAdaptor.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<AlarmElement>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView1AlarmListAdaptor.MyViewHolder {
        val view = inflater.inflate(R.layout.element1_alarm, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView1AlarmListAdaptor.MyViewHolder,
        position: Int
    ) {
        val currentAlarm = alarms[position]
        holder.alarmView.text = currentAlarm.alarmText
        holder.noteView?.text = null //  currentAlarm.noteText?.
    }

    internal fun setAlarms(alarms: List<AlarmElement>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val alarmView: TextView = itemView.findViewById(R.id.textView1_element_alarm_time)
        val noteView: EditText? = itemView.findViewById(R.id.editText2_element_alarm_note)
    }
}
package com.ctrlaccess.multitasker.util

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.Fragment2Alarms
import com.ctrlaccess.multitasker.MainActivity
import com.ctrlaccess.multitasker.R
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import com.ctrlaccess.multitasker.viewModel.entities.DaysOfWeek
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class RecyclerView2AlarmsAdaptor(context: Context) :
    RecyclerView.Adapter<RecyclerView2AlarmsAdaptor.AlarmsViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var alarms = emptyList<Alarm>()
    lateinit var binding: ViewDataBinding
    private val TAG = "RecyclerView2"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmsViewHolder {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.element2_alarms,
            parent,
            false
        )
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


        // update current status
        currentAlarmOnOff(holder, currentAlarm)
        currentAlarmNote(holder.alarmNoteView, currentAlarm.alarmNote.toString())
        currentAlarmDaysOfWeek(holder, currentAlarm.days)
        val alarmDate = currentAlarm.date ?: Calendar.getInstance()
        holder.alarmDateView.text = Alarm.dateFormat(alarmDate)
        holder.alarmTimeView.text = getTime(currentAlarm)
        holder.alarmSpinnerRepeat.setSelection(currentAlarm.repeatMode)


        holder.checkBoxSunday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.sun = !currentAlarm.days.sun
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxMonday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.mon = !currentAlarm.days.mon
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.tue = !currentAlarm.days.tue
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.wed = !currentAlarm.days.wed
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxThursday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.thurs = !currentAlarm.days.thurs
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxFriday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.fri = !currentAlarm.days.fri
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.checkBoxSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
            currentAlarm.days.sat = !currentAlarm.days.sat
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }

        holder.alarmNoteView.addTextChangedListener {
            currentAlarm.alarmNote = holder.alarmNoteView.text.toString()
        }

        holder.alarmNoteView.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val imm = view.context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
            }
        }

        holder.itemView.setOnClickListener {
            currentAlarm.isOn = !currentAlarm.isOn
            Log.d(TAG, " .. checking ...")
            currentAlarmOnOff(holder, currentAlarm)
            val on_off = if (currentAlarm.isOn) "On" else "Off"
            Toast.makeText(
                holder.itemView.context,
                "Alarm is $on_off",
                Toast.LENGTH_SHORT
            ).show()
            MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
        }


        holder.alarmTimeView.setOnLongClickListener { view ->
            updateAlarmTimeAlertDialog(view, position)
            true
        }

        holder.alarmDateWrapper.setOnLongClickListener { view ->
            updateAlarmDateAlertDialog(view, position)
            true
        }

        var tmpPos = -1
        holder.alarmSpinnerRepeat.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "spinner position: $position")
                    if (position != tmpPos) {
                        currentAlarm.repeatMode = position
                        tmpPos = position
                        MainActivity.multitaskViewModel.updateAlarm(currentAlarm)
                        notifyDataSetChanged()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }


    }


    private fun currentAlarmDaysOfWeek(holder: AlarmsViewHolder, daysOfWeek: DaysOfWeek) {

        holder.checkBoxSunday.isChecked = daysOfWeek.sun
        holder.checkBoxMonday.isChecked = daysOfWeek.mon
        holder.checkBoxTuesday.isChecked = daysOfWeek.tue
        holder.checkBoxWednesday.isChecked = daysOfWeek.wed
        holder.checkBoxThursday.isChecked = daysOfWeek.thurs
        holder.checkBoxFriday.isChecked = daysOfWeek.fri
        holder.checkBoxSaturday.isChecked = daysOfWeek.sat

    }

    private fun currentAlarmNote(editText: EditText, note: String?) {
        if (note.isNullOrBlank() or note.equals("null")) {
            editText.setText("")
        } else {
            editText.setText(note)
        }
    }

    private fun currentAlarmOnOff(holder: AlarmsViewHolder, alarm: Alarm) {
        if (alarm.isOn) {
            holder.container.setBackgroundColor(
                holder.container.context.resources.getColor(
                    R.color.alarm_is_on
                )
            )
         } else {
            holder.container.setBackgroundColor(
                holder.container.context.resources.getColor(
                    R.color.alarm_is_off
                )
            )

        }
    }

    private fun updateAlarmDateAlertDialog(view: View, position: Int) {
        val bindingDate = Fragment2Alarms.alert3Binding(view.context)

        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)

        builder.setPositiveButton(R.string.update) { dialog, which ->
            val oldAlarms = Fragment2Alarms.alarms[position]
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, bindingDate.datePicker.year)
                set(Calendar.MONTH, bindingDate.datePicker.month)
                set(Calendar.DAY_OF_MONTH, bindingDate.datePicker.dayOfMonth)
                set(
                    Calendar.HOUR_OF_DAY,
                    oldAlarms.date?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance()
                        .get(Calendar.HOUR_OF_DAY)
                )
                set(
                    Calendar.MINUTE,
                    oldAlarms.date?.get(Calendar.MINUTE) ?: Calendar.getInstance()
                        .get(Calendar.MINUTE)
                )
                set(Calendar.SECOND, 0)
            }
            Log.d(TAG, "set date: ${Date(date.timeInMillis)}")
            oldAlarms.date = date
            MainActivity.multitaskViewModel.updateAlarm(oldAlarms)
            notifyDataSetChanged()
            //dialog.dismiss()
        }

        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }

        builder.setView(bindingDate.root)
        builder.create().show()
    }

    private fun updateAlarmTimeAlertDialog(view: View, position: Int) {

        val bindingAlarm =
            Fragment2Alarms.alert2Binding(view.context)

        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)

        builder.setPositiveButton(R.string.update) { dialog, whick ->

            val oldAlarm = Fragment2Alarms.alarms.get(position)
            val todayDate = Calendar.getInstance()
            val dateTime = Calendar.getInstance().apply {
                set(
                    Calendar.YEAR,
                    oldAlarm.date?.get(Calendar.YEAR) ?: todayDate.get(Calendar.YEAR)
                )
                set(
                    Calendar.MONTH,
                    oldAlarm.date?.get(Calendar.MONTH) ?: todayDate.get(Calendar.MONTH)
                )
                set(
                    Calendar.DAY_OF_MONTH,
                    oldAlarm.date?.get(Calendar.DAY_OF_MONTH)
                        ?: todayDate.get(Calendar.DAY_OF_MONTH)
                )
                set(Calendar.HOUR_OF_DAY, bindingAlarm.timePicker.currentHour)
                set(Calendar.MINUTE, bindingAlarm.timePicker.currentMinute)
                set(Calendar.SECOND, 0)
            }
            Log.d(TAG  , "set time: "+Date(dateTime.timeInMillis).toString())
            oldAlarm.date = dateTime
            MainActivity.multitaskViewModel.updateAlarm(oldAlarm)
            notifyDataSetChanged()
            dialog.dismiss()
        }

        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }

        builder.setView(bindingAlarm.root)
        builder.create().show()

    }

    internal fun setAlarms(alarms: List<Alarm>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

    private fun getTime(alarm: Alarm): String {
        val hr = alarm.date?.get(Calendar.HOUR_OF_DAY) ?: Calendar.getInstance()
            .get(Calendar.HOUR_OF_DAY)
        val min = alarm.date?.get(Calendar.MINUTE) ?: Calendar.getInstance().get(Calendar.MINUTE)
        val time = Time(hr, min, 0)
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(time)
    }


    class AlarmsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val alarmDateView: TextView = itemView.findViewById(R.id.textView_alarm_date)
        val alarmDateWrapper: LinearLayout = itemView.findViewById(R.id.layout_alarm_date)

        val alarmSpinnerRepeat: Spinner = itemView.findViewById(R.id.spinner_repeat_choices)
        val s = ArrayAdapter.createFromResource(
            itemView.context,
            R.array.spinner_repeat_choices,
            R.layout.spinner_repeat_list
        ).also { adapter ->
            alarmSpinnerRepeat.adapter = adapter
        }

        val alarmTimeView: TextView = itemView.findViewById(R.id.textView1_element_alarm_time)
        val alarmNoteView: EditText = itemView.findViewById(R.id.editText2_element_alarm_note)

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




package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.AlarmTime
import com.ctrlaccess.multitasker.databinding.Alert2CreateAlarmListBinding
import com.ctrlaccess.multitasker.databinding.Fragment2AlarmListBinding
import kotlinx.android.synthetic.main.alert2_create_alarm_list.*
import java.sql.Time
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2Alarms.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2Alarms : Fragment() {

    lateinit var recyclerViewAlarmElements: RecyclerView
    lateinit var recyclerView1AlarmsAdaptor: RecyclerView1AlarmsAdaptor
    private var alarms = arrayListOf<Alarm>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment2_alarm_list, container, false)
        val binding = DataBindingUtil.inflate<Fragment2AlarmListBinding>(
            inflater, R.layout.fragment2_alarm_list, container, false
        )
        // use title from alert dialog, when creating new list
        val args = Fragment2AlarmsArgs.fromBundle(requireArguments())
        (activity as ToolbarTitleChangeListener).updateTitle(args.listTitle, args.listSubTitle)


        binding.fab2Alarms.setOnClickListener { v: View ->
            createAlertDialog2()
        }

        recyclerViewAlarmElements = binding.recyclerViewAlarms
        recyclerView1AlarmsAdaptor = RecyclerView1AlarmsAdaptor(requireContext())
        recyclerViewAlarmElements.adapter = recyclerView1AlarmsAdaptor
        recyclerViewAlarmElements.layoutManager = LinearLayoutManager(requireContext())

        (activity as ToolbarTitleChangeListener).showMenu()

        return binding.root
    }

    private fun createAlertDialog2() {
        val bindingAlert = alert1Binding()

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setPositiveButton(R.string.create) { dialog, which ->

            val alarmTime = AlarmTime(
                bindingAlert.timePicker.currentHour,
                bindingAlert.timePicker.currentMinute
            )

            val alarm = Alarm(time = alarmTime)
            alarms.add(alarm)
            recyclerView1AlarmsAdaptor.setAlarms(alarms)
        }

        builder?.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog?.dismiss()
        }
        builder?.setView(bindingAlert.root)
        builder?.create()?.show()
    }

    private fun alert1Binding(): Alert2CreateAlarmListBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.alert2_create_alarm_list, null, false
        )
    }


}
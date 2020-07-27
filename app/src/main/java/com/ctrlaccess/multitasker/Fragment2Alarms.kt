package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.MultitaskerViewModel
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.AlarmTime
import com.ctrlaccess.multitasker.databinding.Alert2CreateAlarmListBinding
import com.ctrlaccess.multitasker.databinding.Fragment2AlarmListBinding
import com.ctrlaccess.multitasker.util.SimpleItemTouchCallback
import com.ctrlaccess.multitasker.util.SwipedElement

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2Alarms.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2Alarms : Fragment()  {

    lateinit var recyclerViewAlarms: RecyclerView
    private lateinit var recyclerView1AlarmsAdaptor: RecyclerView1AlarmsAdaptor


    companion object {
        var alarms = arrayListOf<Alarm>()
        lateinit var binding: Fragment2AlarmListBinding
        lateinit var scheduleTitle: String
        var scheduleNote: String? = null

        fun alert1Binding(context: Context): Alert2CreateAlarmListBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.alert2_create_alarm_list, null, false
            )
        }
    }


    private lateinit var multitaskerViewModel: MultitaskerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment2_alarm_list, container, false)
        binding = DataBindingUtil.inflate<Fragment2AlarmListBinding>(
            inflater, R.layout.fragment2_alarm_list, container, false
        )

        // use title from alert dialog, when creating new list
        val args = Fragment2AlarmsArgs.fromBundle(requireArguments())
        (activity as ToolbarTitleChangeListener).updateTitle(args.listTitle, args.listSubTitle)

        scheduleTitle = args.listTitle
        scheduleNote = args.listSubTitle

        binding.fab2Alarms.setOnClickListener { v: View ->
            createAlertDialog2()
        }

        recyclerViewAlarms = binding.recyclerViewAlarms
        recyclerView1AlarmsAdaptor = RecyclerView1AlarmsAdaptor(requireContext())
        recyclerViewAlarms.adapter = recyclerView1AlarmsAdaptor
        recyclerViewAlarms.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchCallback =
            SimpleItemTouchCallback(recyclerView1AlarmsAdaptor)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewAlarms)


        (activity as ToolbarTitleChangeListener).showMenu()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun createAlertDialog2() {
        val bindingAlert = alert1Binding(requireContext())

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
//            val insertId = (activity as MainActivity).addAlarm(alarm)
//            Toast.makeText(context, "idx: $insertId", Toast.LENGTH_SHORT).show()
            recyclerView1AlarmsAdaptor.setAlarms(alarms)
        }

        builder?.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog?.dismiss()
        }
        builder?.setView(bindingAlert.root)
        builder?.create()?.show()
    }


    override fun onStop() {
        super.onStop()
        Fragment2Alarms.alarms = arrayListOf<Alarm>()
    }



}



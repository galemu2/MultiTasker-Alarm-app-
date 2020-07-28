package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.MultitaskerViewModel
import com.ctrlaccess.multitasker.database.entities.Alarm
import com.ctrlaccess.multitasker.database.entities.AlarmTime
import com.ctrlaccess.multitasker.databinding.Alert2AlarmDialogBinding
import com.ctrlaccess.multitasker.databinding.Fragment2AlarmsBinding
import com.ctrlaccess.multitasker.util.AlarmElementItemTouchCallback
import com.ctrlaccess.multitasker.util.RecyclerView1AlarmsAdaptor

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
        lateinit var binding: Fragment2AlarmsBinding
        lateinit var scheduleTitle: String
        var scheduleNote: String? = null

        fun alert1Binding(context: Context): Alert2AlarmDialogBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.alert2_alarm_dialog, null, false
            )
        }
    }

    private lateinit var multitaskerViewModel: MultitaskerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment2_alarms, container, false)
        binding = DataBindingUtil.inflate<Fragment2AlarmsBinding>(
            inflater, R.layout.fragment2_alarms, container, false
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
        recyclerView1AlarmsAdaptor =
            RecyclerView1AlarmsAdaptor(
                requireContext()
            )
        recyclerViewAlarms.adapter = recyclerView1AlarmsAdaptor
        recyclerViewAlarms.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchCallback =
            AlarmElementItemTouchCallback(recyclerView1AlarmsAdaptor)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewAlarms)

        (activity as ToolbarTitleChangeListener).showMenu()

        return binding.root
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



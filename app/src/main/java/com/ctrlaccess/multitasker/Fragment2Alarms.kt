package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.viewModel.MultitaskerViewModel
import com.ctrlaccess.multitasker.viewModel.entities.Alarm
import com.ctrlaccess.multitasker.viewModel.entities.AlarmTime
import com.ctrlaccess.multitasker.databinding.Alert2AlarmDialogBinding
import com.ctrlaccess.multitasker.databinding.Fragment2AlarmsBinding
import com.ctrlaccess.multitasker.util.AlarmElementItemTouchCallback
import com.ctrlaccess.multitasker.util.RecyclerView2AlarmsAdaptor

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2Alarms.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2Alarms : Fragment() {

    lateinit var recyclerViewAlarms: RecyclerView
    private lateinit var recyclerView2AlarmsAdaptor: RecyclerView2AlarmsAdaptor

    companion object {
        var alarms = arrayListOf<Alarm>()
        lateinit var binding: Fragment2AlarmsBinding
        lateinit var args: Fragment2AlarmsArgs

        fun alert2Binding(context: Context): Alert2AlarmDialogBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.alert2_alarm_dialog, null, false
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment2_alarms, container, false)
        binding = DataBindingUtil.inflate<Fragment2AlarmsBinding>(
            inflater, R.layout.fragment2_alarms, container, false
        )

        // use title from alert dialog, when creating new list
        args = Fragment2AlarmsArgs.fromBundle(requireArguments())
        (activity as ToolbarTitleChangeListener).updateTitle(args.listTitle, args.listSubTitle)

        binding.fab2Alarms.setOnClickListener { v: View ->
            newAlarmAlertDialog(requireContext())
        }

        recyclerViewAlarms = binding.recyclerViewAlarms
        recyclerView2AlarmsAdaptor = RecyclerView2AlarmsAdaptor(requireContext())

        recyclerViewAlarms.adapter = recyclerView2AlarmsAdaptor
        recyclerViewAlarms.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchCallback =
            AlarmElementItemTouchCallback(recyclerView2AlarmsAdaptor)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewAlarms)

        (activity as ToolbarTitleChangeListener).showMenu()

        if (args.scheduleID > 0) {
            Log.d("TAG", "scheduleID: ${args.scheduleID}")
            alarms = MainActivity.multitaskViewModel.getAllAlarms(args.scheduleID) as ArrayList<Alarm>
            recyclerView2AlarmsAdaptor.setAlarms(alarms)
        }

        return binding.root
    }

    // alert dialog created when fab is clicked to add an alarm
    private fun newAlarmAlertDialog(context: Context) {
        val bindingAlert = alert2Binding(context)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setPositiveButton(R.string.create) { dialog, which ->

            val alarmTime = AlarmTime(
                bindingAlert.timePicker.currentHour,
                bindingAlert.timePicker.currentMinute
            )
            val alarm = Alarm(time = alarmTime)

            alarms.add(alarm)
            recyclerView2AlarmsAdaptor.setAlarms(alarms)
        }

        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }
        builder.setView(bindingAlert.root)
        builder.create().show()
    }


    override fun onStop() {
        super.onStop()
        Fragment2Alarms.alarms = arrayListOf<Alarm>()
    }


}



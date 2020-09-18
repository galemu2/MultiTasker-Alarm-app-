package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.databinding.Alert1ScheduleDialogBinding
import com.ctrlaccess.multitasker.databinding.Fragment1SchedulesBinding
import com.ctrlaccess.multitasker.util.RecyclerView1SchedulesAdaptor
import com.ctrlaccess.multitasker.util.ScheduleElementItemTouchCallback
import com.ctrlaccess.multitasker.viewModel.entities.Schedule
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1Schedules.newInstance] factory method to
 * create an instance of this fragment.
 */
open class Fragment1Schedules : Fragment() {

    lateinit var recyclerViewLists: RecyclerView
    lateinit var recyclerViewSchedulesAdaptor: RecyclerView1SchedulesAdaptor

    companion object {
        var schedules = arrayListOf<Schedule>()
        lateinit var binding: Fragment1SchedulesBinding

        //inflate the alert dialog
        fun inflateAlert1Binding(context: Context): Alert1ScheduleDialogBinding {
            return DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.alert1_schedule_dialog, null, false
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<Fragment1SchedulesBinding>(
            inflater, R.layout.fragment1_schedules,
            container, false
        )


        (activity as ToolbarTitleChangeListener).hideMenu()

        binding.fab1List.setOnClickListener {
            createAlertDialog1(requireContext())
        }

        recyclerViewLists = binding.recyclerViewLists
        recyclerViewSchedulesAdaptor = RecyclerView1SchedulesAdaptor(requireContext())
        recyclerViewLists.adapter = recyclerViewSchedulesAdaptor
        recyclerViewLists.layoutManager = LinearLayoutManager(requireContext())

        recyclerViewSchedulesAdaptor.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            fun checkEmpty() {
                val empty = recyclerViewSchedulesAdaptor.itemCount == 0

                if (empty) {
                    recyclerViewLists.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE
                    recyclerViewLists.visibility = View.VISIBLE
                }

            }
        })
        if (recyclerViewLists.isEmpty()) {
            recyclerViewLists.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE

        } else {
            recyclerViewLists.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }

        val itemTouchCallback = ScheduleElementItemTouchCallback(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewLists)

        MainActivity.multitaskViewModel.allSchedules.observe(
            viewLifecycleOwner,
            Observer { schedules ->
                schedules?.let { recyclerViewSchedulesAdaptor.setSchedules(it) }
                Fragment1Schedules.schedules = schedules as ArrayList<Schedule>
            })

        // todo button is used to check database
        binding.buttonAllData.setOnClickListener {

            val alarms = MainActivity.multitaskViewModel.getAllAlarmsInDatabase()
             Log.d("TAG Alarms: ", alarms.toString())
            alarms.forEach { alarm ->
                Log.d("TAG each Alarm: ", Date(alarm.calDate?.timeInMillis ?: 0L).toString())
            }
             val schedules = MainActivity.multitaskViewModel.getAllSchedulesChecker()
            Log.d("TAG Schedule: ", schedules.toString())

        }
        return binding.root
    }

    private fun createAlertDialog1(context: Context) {

        val bindingAlert = inflateAlert1Binding(context)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setView(bindingAlert.root)

        builder.setPositiveButton(
            R.string.create
        ) { dialog, which ->
            //check for title
            if (bindingAlert.editTextText1Title.text.toString().length > 1) {
                startNavigation(
                    bindingAlert.editTextText1Title.text.toString(),
                    bindingAlert.editTextText1Note.text.toString()
                )
            } else
                Toast.makeText(context, "Title required.", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog.dismiss()
        }

        builder.create().show()
    }


    //navigate from fragment1 to fragment2
    private fun startNavigation(listTitle: String, listSubTitle: String?, scheduleId: Long = -1) {
        view?.findNavController()
            ?.navigate(
                Fragment1SchedulesDirections.actionFragmentScheduleListToFragmentAlarmList(
                    listTitle,
                    listSubTitle,
                    scheduleId
                )
            )
    }


}




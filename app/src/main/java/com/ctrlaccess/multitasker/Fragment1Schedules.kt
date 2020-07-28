package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctrlaccess.multitasker.database.MultitaskerViewModel
import com.ctrlaccess.multitasker.database.entities.Schedule
import com.ctrlaccess.multitasker.databinding.Alert1ScheduleDialogBinding
import com.ctrlaccess.multitasker.databinding.Fragment1SchedulesBinding
import com.ctrlaccess.multitasker.util.RecyclerView2SchedulesAdaptor
import com.ctrlaccess.multitasker.util.ScheduleElementItemTouchCallback

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1Schedules.newInstance] factory method to
 * create an instance of this fragment.
 */
open class Fragment1Schedules : Fragment() {

    lateinit var recyclerViewLists: RecyclerView
    lateinit var recyclerViewSchedulesAdaptor: RecyclerView2SchedulesAdaptor

    companion object {
        var schedules = arrayListOf<Schedule>()
        lateinit var binding: Fragment1SchedulesBinding
        private lateinit var multitaskerViewModel: MultitaskerViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment1_schedules, container, false)
        binding = DataBindingUtil.inflate<Fragment1SchedulesBinding>(
            inflater, R.layout.fragment1_schedules,
            container, false
        )
        (activity as ToolbarTitleChangeListener).hideMenu()

        binding.fab1List.setOnClickListener {
            createAlertDialog1()
        }

        multitaskerViewModel = ViewModelProvider(this).get(MultitaskerViewModel::class.java)
        recyclerViewLists = binding.recyclerViewLists
        recyclerViewSchedulesAdaptor = RecyclerView2SchedulesAdaptor(requireContext())
        recyclerViewLists.adapter = recyclerViewSchedulesAdaptor
        recyclerViewLists.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchCallback = ScheduleElementItemTouchCallback(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewLists)

        multitaskerViewModel.allSchedules.observe(viewLifecycleOwner, Observer { schedules ->
            schedules?.let { recyclerViewSchedulesAdaptor.setSchedules(it) }
            Fragment1Schedules.schedules = schedules as ArrayList<Schedule>
        })

        // todo button is used to chek database
        binding.buttonAllData.setOnClickListener {
            Toast.makeText(it.context, "clicked 1 .. 2 .. 3 ..", Toast.LENGTH_SHORT).show()
            val alarms = multitaskerViewModel.getAllAlarmsChecker()
            Log.d("TAG", alarms.toString() + "\n\n")

            val schedules = multitaskerViewModel.getAllSchedulesChecker()
            Log.d("TAG", schedules.toString())

        }
        return binding.root
    }


    private fun createAlertDialog1() {
        val bindingAlert = alert1Binding()

        val builder: AlertDialog.Builder? =
            activity?.let {
                AlertDialog.Builder(it)
            }

        builder?.setView(bindingAlert?.root)

        builder?.setPositiveButton(
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

        builder?.setNegativeButton(R.string.cancel_create) { dialog, which ->
            dialog?.dismiss()
        }

        builder?.create()?.show()
    }

    //inflate the alert dialog
    private fun alert1Binding(): Alert1ScheduleDialogBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.alert1_schedule_dialog, null, false
        )
    }

    //navigate from fragment1 to fragment2
    private fun startNavigation(listTitle: String, listSubTitle: String?) {
        view?.findNavController()
            ?.navigate(
                Fragment1SchedulesDirections.actionFragmentScheduleListToFragmentAlarmList(
                    listTitle,
                    listSubTitle
                )
            )
    }


}




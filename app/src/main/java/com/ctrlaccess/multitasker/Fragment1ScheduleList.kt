package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ctrlaccess.multitasker.databinding.Alert1CreateScheduleListBinding
import com.ctrlaccess.multitasker.databinding.Fragment1ScheduleListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1ScheduleList.newInstance] factory method to
 * create an instance of this fragment.
 */
open class Fragment1ScheduleList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment1_schedule_list, container, false)
        val binding = DataBindingUtil.inflate<Fragment1ScheduleListBinding>(
            inflater, R.layout.fragment1_schedule_list,
            container, false
        )
        (activity as ToolbarTitleChangeListener).hideMenu()

        binding.fab1List.setOnClickListener {
            createAlertDialog1()
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
    private fun alert1Binding(): Alert1CreateScheduleListBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.alert1_create_schedule_list, null, false
        )
    }

    //navigate from fragment1 to fragment2
    private fun startNavigation(listTitle: String, listSubTitle: String?) {
        view?.findNavController()
            ?.navigate(
                Fragment1ScheduleListDirections.actionFragmentScheduleListToFragmentAlarmList(
                    listTitle, listSubTitle
                )
            )
    }

}




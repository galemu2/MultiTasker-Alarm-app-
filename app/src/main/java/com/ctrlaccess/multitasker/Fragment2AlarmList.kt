package com.ctrlaccess.multitasker

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ctrlaccess.multitasker.databinding.Alert2CreateAlarmListBinding
import com.ctrlaccess.multitasker.databinding.Fragment2AlarmListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2AlarmList.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2AlarmList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment2_alarm_list, container, false)
        val binding = DataBindingUtil.inflate<Fragment2AlarmListBinding>(
            inflater, R.layout.fragment2_alarm_list, container, false
        )
        // use title from alert dialog, when creating new list
        val args = Fragment2AlarmListArgs.fromBundle(requireArguments())
        (activity as ToolbarTitleChangeListener).updateTitel(args.listTitle)

        binding.fab2Alarms.setOnClickListener { v: View ->
            createAlertDialog2()
        }

        return binding.root
    }

    private fun createAlertDialog2() {
        val bindingAlert = alert1Binding()

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }


        builder?.setPositiveButton(R.string.create) { dialog, which ->
            var hr: Int?
            var min: Int?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hr = bindingAlert.timePicker.hour
                min = bindingAlert.timePicker.minute
            } else {
                hr = bindingAlert.timePicker.currentHour
                min = bindingAlert.timePicker.currentMinute
            }

            Toast.makeText(context, "Tiem: " + hr + ":" + min, Toast.LENGTH_SHORT).show()
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
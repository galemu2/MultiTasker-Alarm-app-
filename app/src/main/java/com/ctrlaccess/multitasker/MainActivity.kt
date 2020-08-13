package com.ctrlaccess.multitasker

import android.app.AlarmManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ctrlaccess.multitasker.alarm.MyAlarmManager
import com.ctrlaccess.multitasker.viewModel.MultitaskerViewModel
import com.ctrlaccess.multitasker.viewModel.entities.Schedule
import com.ctrlaccess.multitasker.databinding.ActivityMainBinding
import com.ctrlaccess.multitasker.viewModel.entities.Alarm

interface ToolbarTitleChangeListener {
    fun updateTitle(title: String, subTitle: String?)
    fun showMenu()
    fun hideMenu()
}

class MainActivity : AppCompatActivity(), ToolbarTitleChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAlarmManager: MyAlarmManager

    companion object {
        lateinit var multitaskViewModel: MultitaskerViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // setContentView(R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        multitaskViewModel = ViewModelProvider(this).get(MultitaskerViewModel::class.java)
        myAlarmManager = MyAlarmManager(this)

    }

    override fun updateTitle(newTitle: String, newSubTitle: String?) {
        binding.toolbar.title = newTitle
        binding.toolbar.subtitle = newSubTitle ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_alarms_button, menu)
        return true
    }

    override fun showMenu() {
        binding.toolbar.menu.findItem(R.id.button_save_alarms).setVisible(true)
    }

    override fun hideMenu() {
        val saveButton = binding.toolbar.menu.findItem(R.id.button_save_alarms)
        if (saveButton.isVisible) {
            saveButton.isVisible = false
        }
        updateTitle(getString(R.string.app_name), null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.button_save_alarms -> {
                // todo mush choose between saving new schedule or updating old sch
                val newAlarms = Fragment2Alarms.alarms

                if (newAlarms.size > 0) {
                    newAlarms.forEach { alarm ->
                        myAlarmManager.serRepeatingAlarm(alarm)
                    }

                    // 1. create new schedule obj
                    val newSchedule = Schedule(
                        schedule = binding.toolbar.title.toString(),
                        scheduleNote = binding.toolbar.subtitle.toString(),
                        numberOfAlarms = newAlarms.size
                    )

                    if (Fragment2Alarms.args.scheduleID < 0) {

                        // 2. insert the schedule object.  capture the scheduleId
                        val insertedID = multitaskViewModel.insertSchedule(newSchedule)

                        // 3. update the alarms with the schedule id for later reference
                        Alarm.alarmsAddScheduleId(newAlarms, insertedID)

                        // 4. insert the alarms to database
                        multitaskViewModel.insertAlarms(newAlarms)

                    } else {

                        val updatedAlarms =
                            Alarm.alarmsAddScheduleId(newAlarms, Fragment2Alarms.args.scheduleID)

                        multitaskViewModel.insertAlarms(updatedAlarms)
                        multitaskViewModel.updateSchedule(newSchedule)
                        // multitaskViewModel.updateAlarms(newAlarms)

                    }
                    Toast.makeText(
                        applicationContext,
                        "${newAlarms.size} alarms",
                        Toast.LENGTH_SHORT
                    ).show()


                } else {
                    Toast.makeText(applicationContext, "no alarms added", Toast.LENGTH_SHORT).show()
                }

                // 5. navigate to Fragment1
                Fragment2Alarms.binding.root.findNavController()
                    .navigate(Fragment2AlarmsDirections.actionFragment2AlarmsToFragment1Schedules())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
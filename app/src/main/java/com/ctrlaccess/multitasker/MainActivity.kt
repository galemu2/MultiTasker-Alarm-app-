package com.ctrlaccess.multitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ctrlaccess.multitasker.databinding.ActivityMainBinding

interface ToolbarTitleChangeListener {
    fun updateTitle(title: String, subTitle: String?)
    fun showMenu()
    fun hideMenu()
}

class MainActivity : AppCompatActivity(), ToolbarTitleChangeListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

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
        val isVisible = saveButton.isVisible
        if (isVisible) {
            saveButton.isVisible = false
        }
        updateTitle(getString(R.string.app_name), null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_save_alarms -> {
                Toast.makeText(
                    applicationContext,
                    "save clicked",
                    Toast.LENGTH_SHORT
                )
                    .show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }


}
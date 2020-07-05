package com.ctrlaccess.multitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctrlaccess.multitasker.databinding.ActivityMainBinding

interface ToolbarTitleChangeListener {
    fun updateTitel(title: String)
}

class MainActivity : AppCompatActivity(), ToolbarTitleChangeListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)

    }

    override fun updateTitel(newTitle: String) {
        binding.toolbar.title = newTitle
    }

}
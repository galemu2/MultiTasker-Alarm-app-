package com.ctrlaccess.multitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ctrlaccess.multitasker.databinding.ActivityMainBinding

interface ToolbarTitleChangeListener {
    fun updateTitle(title: String, subTitle: String?)
}

class MainActivity : AppCompatActivity(), ToolbarTitleChangeListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main)


    }

    override fun updateTitle(newTitle: String, newSubTitle: String?) {
        binding.toolbar.title = newTitle
        binding.toolbar.subtitle = newSubTitle ?: ""
    }

}
package com.example.android.kevkane87.matchedbetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.android.kevkane87.matchedbetapp.databinding.ActivityMainBinding
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentCalculatorBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }


}
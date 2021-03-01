package com.example.weatherrx.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherrx.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Router.initNavController(this)
    }
}
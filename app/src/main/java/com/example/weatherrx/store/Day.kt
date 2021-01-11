package com.example.weatherrx.store

import java.util.*


data class Day(val date: Date, var temperature: String = "", var icon: String = "", var hours: MutableList<String> = mutableListOf())
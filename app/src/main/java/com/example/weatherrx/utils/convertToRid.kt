package com.example.weatherrx.utils

import com.example.weatherrx.R
import kotlin.math.roundToInt


fun Int.toCardinalPoints(): Int =
    when (this) {
        in 0..22 -> R.string.north
        in 23..67 -> R.string.northeast
        in 68..112 -> R.string.east
        in 113..157 -> R.string.southeast
        in 158..202 -> R.string.south
        in 203..247 -> R.string.southwest
        in 248..292 -> R.string.west
        in 293..337 -> R.string.northwest
        in 338..360 -> R.string.north
        else -> 0
    }


fun Double.toTemperature(): String {
    val temp = this.roundToInt()
    return when {
        temp > 0 -> "+${temp}°C"
        else -> "${temp}°C"
    }
}


package com.example.weatherrx.data.entities.response

import com.example.weatherrx.data.entities.Day
import com.google.gson.annotations.SerializedName

class ResponseDays(
    @SerializedName("daily")
    val days: List<Day>
)
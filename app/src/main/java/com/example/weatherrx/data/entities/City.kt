package com.example.weatherrx.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "city"
)
data class City(

//    val count: Int = 0,
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val position: Int,
)
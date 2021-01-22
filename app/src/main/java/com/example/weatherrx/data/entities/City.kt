package com.example.weatherrx.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "city",
    indices = [Index(
        value = [ "id"],
        unique = true
    )]
)
data class City(
    @PrimaryKey(autoGenerate = true)
    val count: Int = 0,
    val id: Int,
    val position: Long
)
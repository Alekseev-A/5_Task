package com.example.weatherrx.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "city")
@Parcelize
data class City(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val position: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
) : Parcelable
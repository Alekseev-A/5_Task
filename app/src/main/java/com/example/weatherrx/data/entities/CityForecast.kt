package com.example.weatherrx.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "city_forecast",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = ["id"],
        childColumns = ["cityId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
@Parcelize
data class CityForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dt: Long,
    val cityId: Long,
    val icon: String,
    val pressure: Int,
    val temp: Double,
    val windDeg: Int,
    val windSpeed: Double,
) : Parcelable
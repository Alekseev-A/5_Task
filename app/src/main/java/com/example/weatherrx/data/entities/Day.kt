package com.example.weatherrx.data.entities

import androidx.room.*

@Entity(
    tableName = "day",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = arrayOf("cityId"),
        childColumns = arrayOf("cityId")
    )]
)
data class Day(
    @PrimaryKey(autoGenerate = true)
    val dayId: Int,
    val cityId: Int,
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val snow: Double,
    val sunrise: Int,
    val sunset: Int,
    @Embedded
    val temp: Temp,

    val wind_deg: Int,
    val wind_speed: Double
) {
    @Ignore
    val weather: List<Weather>? = null

    @Entity(
        tableName = "dayWeather",
        foreignKeys = [ForeignKey(
            entity = Day::class,
            parentColumns = arrayOf("dayId"),
            childColumns = arrayOf("parentId")
        )]
    )
    data class Weather(
        @PrimaryKey(autoGenerate = true)
        val weatherId: Int,
        @ColumnInfo(index = true)
        val parentId: Int,
        val icon: String,
    )

    data class Temp(
        val day: Double,
    )
}
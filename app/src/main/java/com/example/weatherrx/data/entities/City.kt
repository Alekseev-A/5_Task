package com.example.weatherrx.data.entities

import androidx.room.*

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = true)
    val cityId: Int,
    val position: Int,
    @Embedded
    val coord: Coord,
    val dt: Int,
    val id: Int,
    @Embedded
    val main: Main,
    val name: String,

    @Embedded
    val wind: Wind
) {
    @Ignore
    var weather: List<Weather> = emptyList()

    data class Coord(
        val lon: Double,
        val lat: Double,
    )

    data class Main(
        val pressure: Int,
        val temp: Double,
    )

    @Entity(
        tableName = "cityWeather",
        foreignKeys = [ForeignKey(
            entity = City::class,
            parentColumns = arrayOf("cityId"),
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

    data class Wind(
        val deg: Int,
        val speed: Int
    )
}


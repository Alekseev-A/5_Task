package com.example.weatherrx.data.store

import androidx.room.*
import com.example.weatherrx.data.entities.City


@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getCities(): List<City>

    @Query("SELECT * FROM city WHERE cityId IS :cityId")
    fun getCity(cityId: Int): City?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)
}
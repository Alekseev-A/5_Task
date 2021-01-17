package com.example.weatherrx.data.store

import androidx.room.*
import com.example.weatherrx.data.entities.City
import io.reactivex.rxjava3.core.Observable


@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getCities(): Observable<List<City>>

    @Query("SELECT * FROM city WHERE cityId IS :cityId")
    fun getCity(cityId: Int): Observable<City?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)
}
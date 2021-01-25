package com.example.weatherrx.data.store

import androidx.room.*
import com.example.weatherrx.data.entities.City
import io.reactivex.rxjava3.core.Observable

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getCitiesObservable(): Observable<List<City>>

    @Query("SELECT * FROM city ORDER BY city.position DESC")
    fun getCities(): List<City>

    @Query("SELECT * FROM city WHERE id IS :id")
    fun getCity(id: Int): Observable<City?>

    @Query("SELECT * FROM city WHERE city.position BETWEEN :from and :to")
    fun getCitiesBetweenPositions(from: Int, to : Int): List<City>

    @Transaction
    fun insertCity(id: Long) = insertCity(
        City(
            id = id,
            position = getMaxPosition() + 1
        )
    )

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCity(city: City): Long

    @Update
    fun updateCities(city: List<City>)

    @Query("UPDATE city SET position = :count where id = :id")
    fun updateCityPosition(id: Int, count: Long)

    @Query("SELECT MAX(city.position) FROM city")
    fun getMaxPosition(): Int

    @Delete
    fun deleteCity(city: City)
}
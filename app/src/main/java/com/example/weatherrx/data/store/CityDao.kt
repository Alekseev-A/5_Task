package com.example.weatherrx.data.store

import android.util.Log
import androidx.room.*
import com.example.weatherrx.data.entities.City
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableOnSubscribe
import io.reactivex.rxjava3.core.Observable

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getCities(): Observable<List<City>>

    @Query("SELECT * FROM city WHERE id IS :id")
    fun getCity(id: Int): Observable<City?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertCity(city: City): Long

    @Query("UPDATE city SET position = :position where id = :id")
    fun updateCityPosition(id: Int, position: Long)

    @Transaction
    fun swapPositions(first: City, second:City){
        updateCityPosition(first.id, second.position)
        updateCityPosition(second.id, first.position)
    }

    @Transaction
    fun insertCityWithCurrentPosition(id : Int) {
        Log.d("TAG", "insertCityWithCurrentPosition: before")
        val position = insertCity(City(id = id, position = -1))
        Log.d("TAG", "insertCityWithCurrentPosition: after $position")
        updateCityPosition(id, position)
    }

    @Delete
    fun deleteCity(city: City)
}
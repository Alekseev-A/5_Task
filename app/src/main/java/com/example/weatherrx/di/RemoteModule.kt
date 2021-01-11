package com.example.weatherrx.di

import dagger.Module
import dagger.Provides
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.weatherrx.store.Answer
import javax.inject.Singleton


@Module
class RemoteModule {
    interface JsonAnswerHolderApi {
        @GET("forecast")
        fun getAnswer(
            @Query("lat") lat: Double,
            @Query("lon") long: Double,
            @Query("appid") key: String,
            @Query("units") units: String = "metric",
        ): Call<Answer>
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Singleton @Provides fun providesJsonHolder(retrofit: Retrofit): JsonAnswerHolderApi =
            retrofit.create(JsonAnswerHolderApi::class.java)

}
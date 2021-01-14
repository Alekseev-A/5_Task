package com.example.weatherrx.di

import com.example.weatherrx.data.OpenWeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RemoteModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesJsonHolder(retrofit: Retrofit): OpenWeatherApi =
        retrofit.create(OpenWeatherApi::class.java)
}
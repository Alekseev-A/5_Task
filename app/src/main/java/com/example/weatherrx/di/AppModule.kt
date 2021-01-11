package com.example.weatherrx.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import com.example.weatherrx.App
import javax.inject.Singleton


@Module
class AppModule(val app: App) {
    @Singleton @Provides fun providesApplication(): Application = app
    @Singleton @Provides fun providesContext(application: Application): Context = application.applicationContext
}
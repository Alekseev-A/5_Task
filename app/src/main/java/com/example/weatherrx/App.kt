package com.example.weatherrx

import android.app.Application
import com.example.weatherrx.di.AppComponent
import com.example.weatherrx.di.AppModule
import com.example.weatherrx.di.DaggerAppComponent

class App : Application() {
    companion object {
        var dagger: AppComponent? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        dagger = DaggerAppComponent.builder().appModule(AppModule(app = this@App)).build()
    }
}
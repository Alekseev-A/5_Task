package com.example.weatherrx

import android.app.Application
import com.example.weatherrx.di.AppComponent
import com.example.weatherrx.di.DaggerAppComponent
import com.example.weatherrx.di.AppModule

/**
 * Created by Artem Alexeev on 21.10.2020.
 * Application for this app.
 * Used for a Dagger.
 */
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
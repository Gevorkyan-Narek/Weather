package com.example.weather

import android.app.Application
import com.example.weather.modules.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(koinModules)
        }
    }

}
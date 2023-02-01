package com.weather.weather

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.weather.weather.modules.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(applicationContext)
        startKoin {
            androidContext(this@BaseApplication)
            modules(koinModules)
        }
    }

}
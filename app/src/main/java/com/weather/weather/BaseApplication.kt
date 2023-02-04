package com.weather.weather

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.weather.weather.modules.koinModules
import com.weather.weather.work.manager.RemoveOldDataWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class BaseApplication : Application() {

    companion object {
        private const val REMOVE_OLD_DATA = "REMOVE_OLD_DATA"
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(applicationContext)
        startKoin {
            androidContext(this@BaseApplication)
            modules(koinModules)
        }
        workerInit()
    }

    private fun workerInit() {
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            REMOVE_OLD_DATA,
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<RemoveOldDataWorker>(
                repeatInterval = 3,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .addTag(REMOVE_OLD_DATA)
                .build()
        )
    }

}
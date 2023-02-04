package com.weather.weather.work.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.weather.core.data.api.ForecastRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RemoveOldDataWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val forecastRepository by inject<ForecastRepository>()

    private val handler = CoroutineExceptionHandler { _, exception ->
        logger.error(exception.localizedMessage, exception)
    }

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO + SupervisorJob() + handler) {
            forecastRepository.removeOldForecast()
            logger.info("Removed")
        }
        return Result.success()
    }

}
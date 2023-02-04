package com.weather.core.data.impl.forecast

import com.weather.android.utils.checkResult
import com.weather.android.utils.mapList
import com.weather.android.utils.safeApiCall
import com.weather.base.utils.DateFormatter
import com.weather.base.utils.DateFormatter.toEpochSecond
import com.weather.core.data.api.ForecastRepository
import com.weather.core.datasource.db.forecast.ForecastDao
import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.threeten.bp.LocalDateTime

class ForecastRepositoryImpl(
    private val api: ForecastApi,
    private val dao: ForecastDao,
    private val mapper: ForecastMapper,
) : ForecastRepository {

    companion object {
        private const val APP_ID = "de20ab79873ea9f86f97dd46abb198ec"
        private const val THREE_HOUR = 3L
    }

    override val isDownloading = MutableStateFlow<String?>(null)

    override suspend fun downloadForecast(cityDomain: CityDomain) {
        safeApiCall {
            isDownloading.emit(cityDomain.name)
            api.downloadForecast(
                lat = cityDomain.lat,
                lon = cityDomain.lon,
                APP_ID
            )
        }.checkResult { response ->
            isDownloading.emit(null)
            dao.insertReplace(response.forecast.map(mapper::toEntity))
        }
    }

    override fun getTodayForecast(): Flow<List<WeatherDomain>> {
        return dao
            .getNearestForecast(DateFormatter.getNextDateTime())
            .mapList(mapper::toDomain)
    }

    override fun getForecast(): Flow<List<WeatherDomain>> {
        return dao.getForecast().mapList(mapper::toDomain)
    }

    override suspend fun removeOldForecast() {
        LocalDateTime.now().minusHours(THREE_HOUR).toEpochSecond().apply {
            dao.removeBefore(this)
        }
    }

}
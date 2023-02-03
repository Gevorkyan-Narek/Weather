package com.weather.core.data.impl.forecast

import com.weather.android.utils.checkResult
import com.weather.android.utils.mapList
import com.weather.android.utils.safeApiCall
import com.weather.base.utils.DateFormatter
import com.weather.core.data.api.ForecastRepository
import com.weather.core.datasource.db.forecast.ForecastDao
import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.domain.models.forecast.WeatherDomain
import com.weather.core.domain.models.geo.CityDomain
import kotlinx.coroutines.flow.Flow

class ForecastRepositoryImpl(
    private val api: ForecastApi,
    private val dao: ForecastDao,
    private val mapper: ForecastMapper,
) : ForecastRepository {

    companion object {
        private const val APP_ID = "de20ab79873ea9f86f97dd46abb198ec"
    }

    override suspend fun downloadForecast(cityDomain: CityDomain) {
        safeApiCall {
            api.downloadForecast(
                lat = cityDomain.lat,
                lon = cityDomain.lon,
                APP_ID
            )
        }.checkResult { response ->
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

}
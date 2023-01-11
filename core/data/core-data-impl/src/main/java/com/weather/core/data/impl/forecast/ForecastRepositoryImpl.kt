package com.weather.core.data.impl.forecast

import com.weather.android.utils.fragment.checkResult
import com.weather.android.utils.fragment.safeApiCall
import com.weather.core.data.api.ForecastRepository
import com.weather.core.datasource.net.forecast.ForecastApi
import com.weather.core.domain.models.forecast.ForecastDomain
import com.weather.core.domain.models.geo.CityDomain

class ForecastRepositoryImpl(
    private val api: ForecastApi,
    private val mapper: GeoMapper
) : ForecastRepository {

    companion object {
        private const val APP_ID = "de20ab79873ea9f86f97dd46abb198ec"
    }

    override suspend fun getForecast(cityDomain: CityDomain): ForecastDomain? {
        return safeApiCall {
            api.downloadForecast(
                lat = cityDomain.lat,
                lon = cityDomain.lon,
                APP_ID
            )
        }.checkResult { response ->
            mapper.toDomain(response)
        }
    }
}
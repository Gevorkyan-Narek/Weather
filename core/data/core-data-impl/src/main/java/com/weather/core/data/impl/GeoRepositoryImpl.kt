package com.weather.core.data.impl

import com.weather.android.utils.fragment.safeApiCall
import com.weather.core.data.api.GeoRepository
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.core.domain.models.ResultWrapper
import com.weather.core.domain.models.geo.GeoDomain

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val mapper: GeoMapper
) : GeoRepository {

    override suspend fun getCities(namePrefix: String?, offset: Int): ResultWrapper<GeoDomain> {
        return safeApiCall {
            val geoResponse = api.getCities(
                namePrefix = namePrefix,
                offset = offset,
                limit = 10,
                languageCode = "ru"
            )
            mapper.toDomain(geoResponse)
        }
    }

}
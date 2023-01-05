package com.example.core.data.impl

import com.example.android.utils.fragment.safeApiCall
import com.example.core.data.api.GeoRepository
import com.example.core.datasource.net.geo.GeoApi
import com.example.core.domain.models.ResultWrapper
import com.example.core.domain.models.geo.GeoDomain

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val mapper: GeoMapper
) : GeoRepository {

    override suspend fun getCities(namePrefix: String, offset: Int): ResultWrapper<GeoDomain> {
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
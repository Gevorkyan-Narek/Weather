package com.weather.core.data.impl.geo

import com.weather.android.utils.fragment.checkResult
import com.weather.android.utils.fragment.safeApiCall
import com.weather.core.data.api.GeoRepository
import com.weather.core.datasource.inmemory.GeoInMemoryStore
import com.weather.core.datasource.inmemory.model.GeoRelEnumsInMemory
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.flow.first
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val inMemoryStore: GeoInMemoryStore,
    private val mapper: GeoMapper,
    private val logger: Logger = LoggerFactory.getLogger(GeoRepositoryImpl::class.java)
) : GeoRepository {

    override suspend fun downloadCities(namePrefix: String, offset: Int): GeoDomain? {
        return safeApiCall {
            api.downloadCities(
                namePrefix = namePrefix,
                offset = offset,
                limit = 10,
                languageCode = "ru"
            )
        }.checkResult { response ->
            val domain = mapper.toDomain(response)
            inMemoryStore.saveInMemory(mapper.toMemory(domain))
            domain
        }
    }

    override suspend fun downloadMoreCities(): GeoDomain? {
        val geoLink = inMemoryStore.geoInMemoryState
            .first()
            .links
            .find { link -> link.rel == GeoRelEnumsInMemory.NEXT } ?: return null

        logger.info("geoLink: $geoLink")
        return safeApiCall {
            api.downloadMoreCities(geoLink.href)
        }.checkResult { response ->
            val domain = mapper.toDomain(response)
            inMemoryStore.saveInMemory(mapper.toMemory(domain))
            domain
        }
    }

}
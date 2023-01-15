package com.weather.core.data.impl.geo

import com.weather.android.utils.checkResult
import com.weather.android.utils.safeApiCall
import com.weather.core.data.api.GeoRepository
import com.weather.core.datasource.inmemory.GeoInMemoryStore
import com.weather.core.datasource.inmemory.model.GeoRelEnumsInMemory
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.core.domain.models.geo.GeoDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val inMemoryStore: GeoInMemoryStore,
    private val mapper: GeoMapper,
    private val logger: Logger = LoggerFactory.getLogger(GeoRepositoryImpl::class.java),
) : GeoRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCities(): Flow<GeoDomain?> {
        return inMemoryStore.geoInMemoryState.mapLatest(mapper::toDomain)
    }

    override suspend fun downloadCities(namePrefix: String, offset: Int) {
        return safeApiCall {
            api.downloadCities(
                namePrefix = namePrefix,
                offset = offset,
                limit = 10,
                languageCode = "ru"
            )
        }.checkResult { response ->
            inMemoryStore.saveInMemory(mapper.toMemory(response))
        }
    }

    override suspend fun downloadMoreCities(): Boolean {
        val geoLink = inMemoryStore.geoInMemoryState
            .firstOrNull()
            ?.links
            ?.find { link -> link.rel == GeoRelEnumsInMemory.NEXT }

        logger.info("geoLink: $geoLink")
        return if (geoLink != null) {
            safeApiCall {
                api.downloadMoreCities(geoLink.href)
            }.checkResult { response ->
                inMemoryStore.saveInMemory(mapper.toMemory(response))
            }
            true
        } else {
            false
        }
    }

}
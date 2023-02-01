package com.weather.core.data.impl.geo

import com.weather.android.utils.checkResult
import com.weather.android.utils.mapList
import com.weather.android.utils.safeApiCall
import com.weather.base.utils.exist
import com.weather.core.data.api.GeoRepository
import com.weather.core.datasource.db.geo.CityDao
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.core.datasource.net.geo.model.GeoResponse
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val dao: CityDao,
    private val mapper: GeoMapper,
    private val logger: Logger = LoggerFactory.getLogger(GeoRepositoryImpl::class.java),
) : GeoRepository {

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 10
        private const val LANG_CODE = "ru"
    }

    override val savedCities = dao.getCities().mapList(mapper::toDomain)

    private val _downloadedCities =
        MutableSharedFlow<GeoResponse>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val downloadedCities = _downloadedCities.map(mapper::toDomain)

    override val selectedCity = dao.selectedCities().filterNotNull().map(mapper::toDomain)

    private val loadMoreLock = Mutex()

    override suspend fun downloadCities(namePrefix: String) {
        logger.info("Download city: $namePrefix")
        loadMoreLock.withLock {
            return safeApiCall {
                api.downloadCities(
                    namePrefix = namePrefix,
                    offset = OFFSET,
                    limit = LIMIT,
                    languageCode = LANG_CODE
                )
            }.checkResult(success = _downloadedCities::emit)
        }
    }

    override suspend fun downloadMoreCities() {
        if (!loadMoreLock.isLocked) {
            downloadedCities.first()
                .links
                .find { link -> link.rel == GeoRelEnumsDomain.NEXT }
                ?.let { geoLink ->
                    safeApiCall {
                        api.downloadMoreCities(geoLink.href)
                    }.checkResult(success = _downloadedCities::emit)
                }
        }
    }

    override suspend fun saveCity(city: CityDomain) {
        val entity = mapper.toEntity(city)
        dao.insertReplace(entity)
        dao.updateSelectedCity(entity.copy(isSelected = true))
    }

    override suspend fun updateSelectedCity(city: CityDomain) {
        dao.updateSelectedCity(mapper.toEntity(city))
    }

    override suspend fun isHasMoreCities(): Boolean {
        return downloadedCities.first().links.exist { link ->
            link.rel == GeoRelEnumsDomain.NEXT
        }
    }

    override suspend fun removeSavedCity(city: CityDomain) {
        dao.remove(mapper.toEntity(city))
        val newSelectedCity = dao.getCities().first().first().copy(isSelected = true)
        if (city.isSelected) {
            dao.updateSelectedCity(newSelectedCity)
        }
    }

}
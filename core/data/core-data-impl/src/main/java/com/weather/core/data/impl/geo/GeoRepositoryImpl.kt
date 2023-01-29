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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GeoRepositoryImpl(
    private val api: GeoApi,
    private val dao: CityDao,
    private val mapper: GeoMapper,
    private val logger: Logger = LoggerFactory.getLogger(GeoRepositoryImpl::class.java),
) : GeoRepository {

    override val savedCities = dao.getCities().mapList(mapper::toDomain)

    private val _downloadedCities =
        MutableSharedFlow<GeoResponse>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val downloadedCities = _downloadedCities.map(mapper::toDomain)

    override val selectedCity = dao.selectedCities().filterNotNull().map(mapper::toDomain)

    override suspend fun downloadCities(namePrefix: String, offset: Int) {
        logger.info("Download city: $namePrefix, offset: $offset")
        return safeApiCall {
            api.downloadCities(
                namePrefix = namePrefix,
                offset = offset,
                limit = 10,
                languageCode = "ru"
            )
        }.checkResult(success = _downloadedCities::emit)
    }

    override suspend fun downloadMoreCities() {
        downloadedCities.first()
            .links
            .find { link -> link.rel == GeoRelEnumsDomain.NEXT }
            ?.let { geoLink ->
                logger.info("Download more cities")
                safeApiCall {
                    api.downloadMoreCities(geoLink.href)
                }.checkResult(success = _downloadedCities::emit)
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
        if (city.isSelected) {
            dao.setSelectedCity(dao.getCities().first().first())
        }
        dao.remove(mapper.toEntity(city))
    }

}
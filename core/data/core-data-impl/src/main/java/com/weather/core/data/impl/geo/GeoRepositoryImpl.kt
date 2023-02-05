package com.weather.core.data.impl.geo

import com.weather.android.utils.mapList
import com.weather.core.data.api.GeoRepository
import com.weather.core.datasource.db.geo.CityDao
import com.weather.core.datasource.net.geo.GeoApi
import com.weather.core.domain.models.DownloadStateDomain
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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

    override val selectedCity = dao.selectedCities().filterNotNull().map(mapper::toDomain)

    override suspend fun downloadCities(namePrefix: String): DownloadStateDomain {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.downloadCities(
                    namePrefix = namePrefix,
                    offset = OFFSET,
                    limit = LIMIT,
                    languageCode = LANG_CODE
                )
                logger.debug("Downloaded: $namePrefix")
                DownloadStateDomain.Success(mapper.toDomain(response))
            }
        } catch (e: Exception) {
            DownloadStateDomain.Error
        }
    }

    override suspend fun downloadNextCities(geoLink: GeoLinkDomain): DownloadStateDomain {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.downloadMoreCities(geoLink.href)
                logger.debug("New downloaded: $geoLink\n$response")
                DownloadStateDomain.Success(mapper.toDomain(response))
            }
        } catch (e: Exception) {
            DownloadStateDomain.Error
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

    override suspend fun removeSavedCity(city: CityDomain) {
        dao.remove(mapper.toEntity(city))
        val newSelectedCity = dao.getCities().first().first().copy(isSelected = true)
        if (city.isSelected) {
            dao.updateSelectedCity(newSelectedCity)
        }
    }

}
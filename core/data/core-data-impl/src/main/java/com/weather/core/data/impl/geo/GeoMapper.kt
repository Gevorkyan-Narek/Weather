package com.weather.core.data.impl.geo

import com.weather.core.datasource.db.geo.CityEntity
import com.weather.core.datasource.net.geo.model.CityResponse
import com.weather.core.datasource.net.geo.model.GeoLinkResponse
import com.weather.core.datasource.net.geo.model.GeoRelEnumsResponse
import com.weather.core.datasource.net.geo.model.GeoResponse
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain

class GeoMapper {

    fun toDomain(net: GeoResponse): GeoDomain = net.run {
        GeoDomain(
            data = data.map(::toDomain),
            links = links?.mapNotNull(::toDomain).orEmpty()
        )
    }

    fun toDomain(net: CityResponse): CityDomain = net.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = false
        )
    }

    fun toDomain(net: GeoLinkResponse?): GeoLinkDomain? = net?.run {
        GeoLinkDomain(
            rel = toDomain(rel),
            href = href
        )
    }

    fun toDomain(net: GeoRelEnumsResponse): GeoRelEnumsDomain = when (net) {
        GeoRelEnumsResponse.FIRST -> GeoRelEnumsDomain.FIRST
        GeoRelEnumsResponse.NEXT -> GeoRelEnumsDomain.NEXT
        GeoRelEnumsResponse.PREV -> GeoRelEnumsDomain.PREV
        GeoRelEnumsResponse.LAST -> GeoRelEnumsDomain.LAST
    }

    fun toDomain(entity: CityEntity): CityDomain = entity.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = entity.isSelected
        )
    }

    fun toEntity(domain: CityDomain): CityEntity = domain.run {
        CityEntity(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = domain.isSelected
        )
    }

}
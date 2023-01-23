package com.weather.core.data.impl.geo

import com.weather.core.datasource.db.geo.CityEntity
import com.weather.core.datasource.inmemory.model.CityInMemory
import com.weather.core.datasource.inmemory.model.GeoInMemory
import com.weather.core.datasource.inmemory.model.GeoLinkInMemory
import com.weather.core.datasource.inmemory.model.GeoRelEnumsInMemory
import com.weather.core.datasource.net.geo.model.CityResponse
import com.weather.core.datasource.net.geo.model.GeoLinkResponse
import com.weather.core.datasource.net.geo.model.GeoRelEnumsResponse
import com.weather.core.datasource.net.geo.model.GeoResponse
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import com.weather.core.domain.models.geo.GeoLinkDomain
import com.weather.core.domain.models.geo.GeoRelEnumsDomain

class GeoMapper {

    fun toDomain(inMemory: GeoInMemory): GeoDomain = inMemory.run {
        GeoDomain(
            data = data.map(::toDomain),
            links = links.mapNotNull(::toDomain)
        )
    }

    fun toDomain(inMemory: CityInMemory): CityDomain = inMemory.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toDomain(entity: CityEntity): CityDomain = entity.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toDomain(inMemory: GeoLinkInMemory?): GeoLinkDomain? = inMemory?.run {
        GeoLinkDomain(
            rel = toDomain(rel),
            href = href
        )
    }

    fun toDomain(inMemory: GeoRelEnumsInMemory): GeoRelEnumsDomain = when (inMemory) {
        GeoRelEnumsInMemory.FIRST -> GeoRelEnumsDomain.FIRST
        GeoRelEnumsInMemory.NEXT -> GeoRelEnumsDomain.NEXT
        GeoRelEnumsInMemory.PREV -> GeoRelEnumsDomain.PREV
        GeoRelEnumsInMemory.LAST -> GeoRelEnumsDomain.LAST
    }

    fun toMemory(net: GeoResponse): GeoInMemory = net.run {
        GeoInMemory(
            data = data.map(::toMemory),
            links = links?.map(::toMemory).orEmpty()
        )
    }

    fun toMemory(net: CityResponse): CityInMemory = net.run {
        CityInMemory(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toMemory(net: GeoLinkResponse): GeoLinkInMemory = net.run {
        GeoLinkInMemory(
            rel = toMemory(rel),
            href = href
        )
    }

    fun toMemory(net: GeoRelEnumsResponse): GeoRelEnumsInMemory = when (net) {
        GeoRelEnumsResponse.FIRST -> GeoRelEnumsInMemory.FIRST
        GeoRelEnumsResponse.NEXT -> GeoRelEnumsInMemory.NEXT
        GeoRelEnumsResponse.PREV -> GeoRelEnumsInMemory.PREV
        GeoRelEnumsResponse.LAST -> GeoRelEnumsInMemory.LAST
    }

    fun toEntity(domain: CityDomain): CityEntity = domain.run {
        CityEntity(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = false
        )
    }

}
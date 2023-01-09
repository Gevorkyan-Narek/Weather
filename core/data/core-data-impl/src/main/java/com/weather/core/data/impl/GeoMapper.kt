package com.weather.core.data.impl

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

    fun toDomain(net: GeoResponse): GeoDomain = net.run {
        GeoDomain(
            data = data.map(::toDomain),
            links = links?.map(::toDomain).orEmpty()
        )
    }

    fun toDomain(net: CityResponse): CityDomain = net.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toDomain(net: GeoLinkResponse): GeoLinkDomain = net.run {
        GeoLinkDomain(
            rel = toDomain(rel),
            href = href
        )
    }

    fun toDomain(net: GeoRelEnumsResponse): GeoRelEnumsDomain = when (net) {
        GeoRelEnumsResponse.FIRST -> GeoRelEnumsDomain.FIRST
        GeoRelEnumsResponse.NEXT -> GeoRelEnumsDomain.NEXT
        GeoRelEnumsResponse.LAST -> GeoRelEnumsDomain.LAST
    }

    fun toMemory(domain: GeoDomain): GeoInMemory = domain.run {
        GeoInMemory(
            data = data.map(::toMemory),
            links = links.map(::toMemory)
        )
    }

    fun toMemory(domain: CityDomain): CityInMemory = domain.run {
        CityInMemory(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toMemory(domain: GeoLinkDomain): GeoLinkInMemory = domain.run {
        GeoLinkInMemory(
            rel = toMemory(rel),
            href = href
        )
    }

    fun toMemory(domain: GeoRelEnumsDomain): GeoRelEnumsInMemory = when (domain) {
        GeoRelEnumsDomain.FIRST -> GeoRelEnumsInMemory.FIRST
        GeoRelEnumsDomain.NEXT -> GeoRelEnumsInMemory.NEXT
        GeoRelEnumsDomain.LAST -> GeoRelEnumsInMemory.LAST
    }

}
package com.weather.core.data.impl

import com.weather.core.datasource.net.geo.model.CityResponse
import com.weather.core.datasource.net.geo.model.GeoResponse
import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain

class GeoMapper {

    fun toDomain(net: GeoResponse): GeoDomain = net.run {
        GeoDomain(
            data = data.map(::toDomain)
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

}
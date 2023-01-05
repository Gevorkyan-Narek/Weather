package com.example.core.data.impl

import com.example.core.datasource.net.geo.model.CityResponse
import com.example.core.datasource.net.geo.model.GeoResponse
import com.example.core.domain.models.geo.CityDomain
import com.example.core.domain.models.geo.GeoDomain

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
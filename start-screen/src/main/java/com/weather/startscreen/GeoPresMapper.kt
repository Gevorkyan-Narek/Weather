package com.weather.startscreen

import com.weather.core.domain.models.geo.CityDomain
import com.weather.core.domain.models.geo.GeoDomain
import com.weather.startscreen.models.CityPres
import com.weather.startscreen.models.GeoPres

class GeoPresMapper {

    fun toPres(domain: GeoDomain): GeoPres = domain.run {
        GeoPres(
            data = data.map(::toPres)
        )
    }

    fun toPres(domain: CityDomain): CityPres = domain.run {
        CityPres(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon
        )
    }

    fun toDomain(pres: CityPres): CityDomain = pres.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = false
        )
    }
}
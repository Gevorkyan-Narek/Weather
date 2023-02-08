package com.weather.startscreen.mapper

import com.weather.core.domain.models.geo.CityDomain
import com.weather.startscreen.models.CityPres

class GeoPresMapper {

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
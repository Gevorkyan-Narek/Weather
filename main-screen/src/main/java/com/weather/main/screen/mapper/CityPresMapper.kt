package com.weather.main.screen.mapper

import com.weather.core.domain.models.geo.CityDomain
import com.weather.main.screen.city.adapter.model.CityInfoItemPres

class CityPresMapper {

    fun toPres(domain: CityDomain) = domain.run {
        CityInfoItemPres(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = isSelected
        )
    }

    fun toDomain(pres: CityInfoItemPres) = pres.run {
        CityDomain(
            name = name,
            countryCode = countryCode,
            lat = lat,
            lon = lon,
            isSelected = isSelected
        )
    }

}
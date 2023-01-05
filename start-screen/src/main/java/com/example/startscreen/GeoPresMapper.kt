package com.example.startscreen

import com.example.core.domain.models.geo.CityDomain
import com.example.core.domain.models.geo.GeoDomain
import com.example.startscreen.models.CityPres
import com.example.startscreen.models.GeoPres

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

}
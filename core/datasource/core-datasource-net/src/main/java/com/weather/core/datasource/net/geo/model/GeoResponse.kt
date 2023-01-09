package com.weather.core.datasource.net.geo.model

data class GeoResponse(
    val data: List<CityResponse>,
    val links: List<GeoLinkResponse>?
)


package com.weather.core.datasource.inmemory.model

data class GeoInMemory(
    val data: List<CityInMemory>,
    val links: List<GeoLinkInMemory>
)


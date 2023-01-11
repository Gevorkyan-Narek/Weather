package com.weather.core.datasource.inmemory.model

data class GeoLinkInMemory(
    val rel: GeoRelEnumsInMemory,
    val href: String
)
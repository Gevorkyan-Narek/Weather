package com.weather.core.datasource.net.geo.model

import com.google.gson.annotations.SerializedName

data class GeoResponse(
    val data: List<CityResponse>,
    val links: List<GeoLink>
)

data class GeoLink(
    val rel: GeoRelEnums,
    val href: String
)

enum class GeoRelEnums {

    @SerializedName("first")
    FIRST,

    @SerializedName("next")
    NEXT,

    @SerializedName("last")
    LAST
}

package com.weather.core.datasource.net.geo.model

import com.google.gson.annotations.SerializedName

enum class GeoRelEnumsResponse {

    @SerializedName("first")
    FIRST,

    @SerializedName("next")
    NEXT,

    @SerializedName("last")
    LAST
}
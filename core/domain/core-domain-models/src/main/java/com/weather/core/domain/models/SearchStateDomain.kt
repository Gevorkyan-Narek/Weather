package com.weather.core.domain.models

import com.weather.core.domain.models.geo.GeoDomain

sealed class SearchStateDomain {

    object Error : SearchStateDomain()

    object Loading : SearchStateDomain()

    data class Success(val geoDomain: GeoDomain) : SearchStateDomain()

}
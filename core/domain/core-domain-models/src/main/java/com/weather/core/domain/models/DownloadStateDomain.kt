package com.weather.core.domain.models

import com.weather.core.domain.models.geo.GeoDomain

sealed class DownloadStateDomain {

    object NoStart : DownloadStateDomain()

    object Error : DownloadStateDomain()

    object Loading : DownloadStateDomain()

    data class Success(val geoDomain: GeoDomain) : DownloadStateDomain()

}
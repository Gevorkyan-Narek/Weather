package com.weather.main.screen.mapper

import com.weather.core.domain.models.DownloadStateDomain
import com.weather.custom.views.downloadstate.DownloadStatePres

class DownloadStateMapper {

    fun toPres(domain: DownloadStateDomain): DownloadStatePres = when (domain) {
        DownloadStateDomain.SUCCESS -> DownloadStatePres.SUCCESS
        DownloadStateDomain.LOADING -> DownloadStatePres.LOADING
        DownloadStateDomain.ERROR -> DownloadStatePres.ERROR
    }

}
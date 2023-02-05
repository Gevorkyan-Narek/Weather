package com.weather.main.screen.mapper

import com.weather.core.domain.models.DownloadStateEnumDomain
import com.weather.custom.views.downloadstate.DownloadStatePres

class DownloadStateMapper {

    fun toPres(domain: DownloadStateEnumDomain): DownloadStatePres = when (domain) {
        DownloadStateEnumDomain.SUCCESS -> DownloadStatePres.SUCCESS
        DownloadStateEnumDomain.LOADING -> DownloadStatePres.LOADING
        DownloadStateEnumDomain.ERROR -> DownloadStatePres.ERROR
    }

}
package com.weather.custom.views.downloadstate

enum class DownloadStatePres {
    SUCCESS,
    LOADING,
    ERROR;

    companion object {

        fun valueOf(value: Int) = values()[value]

    }
}
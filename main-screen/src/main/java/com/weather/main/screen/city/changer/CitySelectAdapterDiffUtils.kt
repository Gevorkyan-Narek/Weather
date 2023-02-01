package com.weather.main.screen.city.changer

import androidx.recyclerview.widget.DiffUtil

class CitySelectAdapterDiffUtils : DiffUtil.ItemCallback<CityAdapterInfo>() {

    override fun areItemsTheSame(oldItem: CityAdapterInfo, newItem: CityAdapterInfo): Boolean {
        return when {
            oldItem is CityAdapterInfo.CityInfo && newItem is CityAdapterInfo.CityInfo -> true
            oldItem is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> true
            oldItem is CityAdapterInfo.NoMatch && newItem is CityAdapterInfo.NoMatch -> true
            oldItem is CityAdapterInfo.NewCityInfo && newItem is CityAdapterInfo.NewCityInfo -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CityAdapterInfo, newItem: CityAdapterInfo): Boolean {
        return when {
            oldItem is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> true
            oldItem is CityAdapterInfo.NoMatch && newItem is CityAdapterInfo.NoMatch -> true
            oldItem is CityAdapterInfo.NewCityInfo && newItem is CityAdapterInfo.NewCityInfo -> {
                oldItem.city.name == newItem.city.name
            }
            oldItem is CityAdapterInfo.CityInfo && newItem is CityAdapterInfo.CityInfo -> {
                oldItem.city.name == newItem.city.name
            }
            else -> false
        }
    }
}
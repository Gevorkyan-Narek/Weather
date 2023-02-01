package com.weather.startscreen.adapter

import androidx.recyclerview.widget.DiffUtil

class CityAdapterDiffUtils : DiffUtil.ItemCallback<CityAdapterInfo>() {

    override fun areItemsTheSame(oldItem: CityAdapterInfo, newItem: CityAdapterInfo): Boolean {
        return when {
            oldItem is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> true
            oldItem is CityAdapterInfo.Loading && newItem !is CityAdapterInfo.Loading -> false
            oldItem !is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> false
            oldItem is CityAdapterInfo.CityInfo && newItem is CityAdapterInfo.CityInfo -> {
                oldItem.city.name == newItem.city.name
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CityAdapterInfo, newItem: CityAdapterInfo): Boolean {
        return when {
            oldItem is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> true
            oldItem is CityAdapterInfo.Loading && newItem !is CityAdapterInfo.Loading -> false
            oldItem !is CityAdapterInfo.Loading && newItem is CityAdapterInfo.Loading -> false
            oldItem is CityAdapterInfo.CityInfo && newItem is CityAdapterInfo.CityInfo -> {
                oldItem.city == newItem.city
            }
            else -> false
        }
    }
}
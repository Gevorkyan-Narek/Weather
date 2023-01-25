package com.weather.main.screen.dialog.adapter

import androidx.recyclerview.widget.DiffUtil

class CityDiffUtil : DiffUtil.ItemCallback<CityAdapterItemPres>() {

    override fun areItemsTheSame(
        oldItem: CityAdapterItemPres,
        newItem: CityAdapterItemPres,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: CityAdapterItemPres,
        newItem: CityAdapterItemPres,
    ): Boolean {
        return oldItem == newItem
    }
}
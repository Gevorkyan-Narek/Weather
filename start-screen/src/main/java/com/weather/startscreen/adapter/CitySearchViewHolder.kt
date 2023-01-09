package com.weather.startscreen.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.databinding.SuggestionItemsBinding

class CitySearchViewHolder(private val binding: SuggestionItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(city: String) {
        binding.city.text = city
    }

}
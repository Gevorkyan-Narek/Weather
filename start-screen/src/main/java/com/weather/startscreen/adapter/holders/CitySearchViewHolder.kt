package com.weather.startscreen.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.databinding.SuggestionItemsBinding
import com.weather.startscreen.models.CityPres

class CitySearchViewHolder(
    private val binding: SuggestionItemsBinding,
    private val action: (CityPres) -> Unit,
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityPres) {
        binding.city.text = city.name
        binding.root.setOnClickListener {
            action(city)
        }
    }

}
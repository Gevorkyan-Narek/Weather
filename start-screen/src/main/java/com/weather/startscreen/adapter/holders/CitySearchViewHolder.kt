package com.weather.startscreen.adapter.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.databinding.SuggestionItemsBinding
import com.weather.startscreen.models.CityPres

private fun inflateBinding(parent: ViewGroup) = SuggestionItemsBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class CitySearchViewHolder(
    parent: ViewGroup,
    private val binding: SuggestionItemsBinding = inflateBinding(parent),
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityPres, action: (CityPres) -> Unit) {
        binding.city.text = city.name
        binding.root.setOnClickListener {
            action(city)
        }
    }

}
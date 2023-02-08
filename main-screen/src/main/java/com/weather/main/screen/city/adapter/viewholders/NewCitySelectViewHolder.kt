package com.weather.main.screen.city.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.city.adapter.model.CityInfoItemPres
import com.weather.main.screen.databinding.CityItemBinding

private fun inflateBinding(parent: ViewGroup) = CityItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class NewCitySelectViewHolder(
    parent: ViewGroup,
    private val binding: CityItemBinding = inflateBinding(parent),
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityInfoItemPres, onNewCitySelect: (CityInfoItemPres) -> Unit) {
        binding.run {
            city.text = item.name
            delete.isVisible = false
            root.setOnClickListener {
                onNewCitySelect(item)
            }
        }
    }

}
package com.weather.main.screen.city.changer.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.city.changer.model.CityInfoItemPres
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

    fun bind(item: CityInfoItemPres, newCitySelect: (CityInfoItemPres) -> Unit) {
        binding.run {
            city.text = item.name
            root.setOnClickListener {
                newCitySelect(item)
            }
        }
    }

}
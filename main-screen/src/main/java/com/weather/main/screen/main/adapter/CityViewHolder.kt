package com.weather.main.screen.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.getColorIf
import com.weather.main.screen.R
import com.weather.main.screen.databinding.CityItemBinding

class CityViewHolder(
    private val binding: CityItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cityName: String, isSelected: Boolean, action: (String) -> Unit) {
        binding.run {
            city.text = cityName
            city.setTextColor(
                city.getColorIf(
                    isSelected,
                    R.attr.colorPrimary,
                    R.attr.colorPrimaryVariant
                )
            )
            root.setOnClickListener { action(cityName) }
        }
    }

}
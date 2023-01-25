package com.weather.main.screen.dialog.adapter

import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.getColorIf
import com.weather.main.screen.R
import com.weather.main.screen.databinding.CityItemBinding

class CityViewHolder(
    private val binding: CityItemBinding,
    private val action: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityAdapterItemPres) {
        binding.run {
            city.text = item.city
            city.setTextColor(
                city.getColorIf(
                    item.isSelected,
                    R.attr.colorPrimary,
                    R.attr.colorPrimaryVariant
                )
            )
            root.setOnClickListener { action(item.city) }
        }
    }

}
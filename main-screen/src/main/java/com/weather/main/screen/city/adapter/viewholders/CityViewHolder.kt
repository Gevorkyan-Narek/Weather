package com.weather.main.screen.city.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.getColorIf
import com.weather.main.screen.R
import com.weather.main.screen.city.adapter.model.CityInfoItemPres
import com.weather.main.screen.databinding.CityItemBinding

private fun inflateBinding(parent: ViewGroup): CityItemBinding = CityItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class CityViewHolder(
    parent: ViewGroup,
    private val binding: CityItemBinding = inflateBinding(parent),
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CityInfoItemPres,
        onSavedCitySelect: (CityInfoItemPres) -> Unit,
        onDeleteClick: () -> Unit,
    ) {
        binding.run {
            city.text = item.name
            city.setTextColor(
                city.getColorIf(
                    item.isSelected,
                    R.attr.colorPrimary,
                    R.attr.colorPrimaryVariant
                )
            )
            delete.setOnClickListener {
                onDeleteClick()
            }
            root.setOnClickListener {
                onSavedCitySelect(item)
            }
        }
    }

}
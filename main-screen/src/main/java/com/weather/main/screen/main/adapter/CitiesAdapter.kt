package com.weather.main.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.weather.main.screen.databinding.CityItemBinding

class CitiesAdapter(private val action: (String) -> Unit) :
        ListAdapter<String, CityViewHolder>(CityDiffUtil()) {

    private var selectedItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            CityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position), selectedItemPosition == position) { cityName ->
            updateSelectedItemColor(position, cityName)
        }
    }

    private fun updateSelectedItemColor(position: Int, cityName: String) {
        val tempSelectedItem = selectedItemPosition
        selectedItemPosition = position
        action(cityName)
        notifyItemChanged(tempSelectedItem)
        notifyItemChanged(selectedItemPosition)
    }

}
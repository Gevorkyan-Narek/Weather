package com.weather.main.screen.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.weather.main.screen.databinding.CityItemBinding

class CitiesAdapter(private val action: (String) -> Unit) :
        ListAdapter<CityAdapterItemPres, CityViewHolder>(CityDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            CityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
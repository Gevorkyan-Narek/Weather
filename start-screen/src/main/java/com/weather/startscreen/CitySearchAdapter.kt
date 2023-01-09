package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.adapter.CitySearchViewHolder
import com.weather.startscreen.databinding.SuggestionItemsBinding
import com.weather.startscreen.models.CityPres

class CitySearchAdapter : RecyclerView.Adapter<CitySearchViewHolder>() {

    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        return CitySearchViewHolder(
            SuggestionItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CitySearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<CityPres>) {
        items.clear()
        items.addAll(newList.map(CityPres::name))
        notifyDataSetChanged()
    }

    fun addCities(newList: List<CityPres>) {
        val positionNewInserted = items.size.inc()
        items.addAll(newList.map(CityPres::name))
        notifyItemInserted(positionNewInserted)
    }

}
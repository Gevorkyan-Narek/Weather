package com.weather.main.screen.today.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.weather.main.screen.databinding.WeatherItemBinding
import com.weather.main.screen.model.WeatherPres

class TodayTimeAdapter(
    private val action: (WeatherPres) -> Unit,
) : ListAdapter<WeatherPres, TodayTimeViewHolder>(TodayTimeDiffUtil()) {

    private val items = mutableListOf<WeatherPres>()
    private var selectedItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTimeViewHolder {
        return TodayTimeViewHolder(
            binding = WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodayTimeViewHolder, position: Int) {
        holder.bind(items[position], selectedItem == position) { pres ->
            updateSelectedItemColor(position, pres)
        }
    }

    private fun updateSelectedItemColor(position: Int, pres: WeatherPres) {
        val tempSelectedItem = selectedItem
        selectedItem = position
        action(pres)
        notifyItemChanged(tempSelectedItem)
        notifyItemChanged(selectedItem)
    }

    override fun submitList(list: MutableList<WeatherPres>?) {
        super.submitList(items)
    }

    fun updateItems(list: List<WeatherPres>) {
        items.clear()
        items.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}
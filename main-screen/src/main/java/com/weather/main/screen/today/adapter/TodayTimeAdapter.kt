package com.weather.main.screen.today.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.databinding.WeatherItemBinding
import com.weather.main.screen.model.WeatherPres

class TodayTimeAdapter(
    private val action: (WeatherPres) -> Unit,
) : RecyclerView.Adapter<TodayTimeViewHolder>() {

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

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<WeatherPres>) {
        val itemsSize = items.size
        items.clear()
        items.addAll(list)
        notifyItemRangeRemoved(0, itemsSize)
        notifyItemRangeInserted(0, list.size)
    }
}
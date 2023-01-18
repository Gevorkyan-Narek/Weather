package com.weather.main.screen.today.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.databinding.WeatherItemBinding
import com.weather.main.screen.model.WeatherPres

class TodayTimeAdapter(
    private val action: (WeatherPres) -> Unit
) : RecyclerView.Adapter<TodayTimeViewHolder>() {

    private val items = mutableListOf<WeatherPres>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTimeViewHolder {
        return TodayTimeViewHolder(
            binding = WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action
        )
    }

    override fun onBindViewHolder(holder: TodayTimeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<WeatherPres>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}
package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.adapter.CitySearchViewHolder
import com.weather.startscreen.adapter.LoadingViewHolder
import com.weather.startscreen.databinding.LoadingItemBinding
import com.weather.startscreen.databinding.SuggestionItemsBinding
import com.weather.startscreen.models.CityPres

class CitySearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val CITY_INFO_VIEW_TYPE = 0
        const val LOADING_VIEW_TYPE = 1
    }

    private val items = mutableListOf<CityAdapterInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CITY_INFO_VIEW_TYPE -> {
                CitySearchViewHolder(
                    SuggestionItemsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            LOADING_VIEW_TYPE -> {
                LoadingViewHolder(
                    LoadingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw RuntimeException("No view type")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CityAdapterInfo.CityInfo -> CITY_INFO_VIEW_TYPE
            is CityAdapterInfo.Loading -> LOADING_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CityAdapterInfo.CityInfo -> {
                (holder as CitySearchViewHolder).bind(item.cityName)
            }
            is CityAdapterInfo.Loading -> {
                holder as LoadingViewHolder
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<CityPres>) {
        val mappedList = newList.map(CityPres::name).map(CityAdapterInfo::CityInfo)
        items.clear()
        items.addAll(mappedList)
        notifyDataSetChanged()
    }

    fun addCities(newList: List<CityPres>) {
        val mappedList = newList.map(CityPres::name).map(CityAdapterInfo::CityInfo)
        val positionNewInserted = items.size
        items.remove(CityAdapterInfo.Loading)
        items.addAll(mappedList)
        notifyItemInserted(positionNewInserted)
    }

    fun addLoading() {
        val found = items.find { info -> info is CityAdapterInfo.Loading }
        if (found == null) {
            items.add(CityAdapterInfo.Loading)
            notifyItemInserted(items.size.dec())
        }
    }

}
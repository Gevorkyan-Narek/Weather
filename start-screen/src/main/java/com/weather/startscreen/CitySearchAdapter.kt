package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.shared.styles.databinding.LoadingItemBinding
import com.weather.startscreen.adapter.CityAdapterDiffUtils
import com.weather.startscreen.adapter.CityAdapterInfo
import com.weather.startscreen.adapter.holders.CitySearchViewHolder
import com.weather.startscreen.adapter.holders.LoadingViewHolder
import com.weather.startscreen.adapter.holders.NoMatchViewHolder
import com.weather.startscreen.databinding.NoMatchItemBinding
import com.weather.startscreen.databinding.SuggestionItemsBinding
import com.weather.startscreen.models.CityPres

class CitySearchAdapter(
    private val citySelectListener: (CityPres) -> Unit,
) : ListAdapter<CityAdapterInfo, RecyclerView.ViewHolder>(CityAdapterDiffUtils()) {

    private val items = mutableListOf<CityAdapterInfo>()

    private companion object {
        const val CITY_INFO_VIEW_TYPE = 0
        const val LOADING_VIEW_TYPE = 1
        const val NO_MATCH_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CITY_INFO_VIEW_TYPE -> {
                CitySearchViewHolder(
                    SuggestionItemsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    citySelectListener
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
            NO_MATCH_VIEW_TYPE -> {
                NoMatchViewHolder(
                    NoMatchItemBinding.inflate(
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

    override fun submitList(list: MutableList<CityAdapterInfo>?) {
        super.submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CityAdapterInfo.CityInfo -> CITY_INFO_VIEW_TYPE
            is CityAdapterInfo.Loading -> LOADING_VIEW_TYPE
            is CityAdapterInfo.NoMatch -> NO_MATCH_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CityAdapterInfo.CityInfo -> {
                (holder as CitySearchViewHolder).bind(item.city)
            }
            is CityAdapterInfo.Loading -> {
                holder as LoadingViewHolder
            }
            is CityAdapterInfo.NoMatch -> {
                holder as NoMatchViewHolder
            }
        }
    }

    fun updateItems(list: List<CityAdapterInfo>) {
        clear()
        items.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun loadMore(list: List<CityAdapterInfo>) {
        items.removeIf { item -> item is CityAdapterInfo.Loading }.apply {
            if (this) {
                notifyItemRemoved(items.size + 1)
            }
        }
        val tempSize = items.size
        items.addAll(list)
        notifyItemRangeInserted(tempSize, items.size)
    }

    fun addLoading() {
        if (!items.contains(CityAdapterInfo.Loading)) {
            items.add(CityAdapterInfo.Loading)
            notifyItemInserted(items.size)
        }
    }

    fun clearAndShowLoading() {
        if (items.isEmpty() || items.size != items.filterIsInstance<CityAdapterInfo.Loading>().size) {
            clear()
            addLoading()
        }
    }

    private fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

}
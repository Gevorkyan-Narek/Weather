package com.weather.startscreen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.adapter.holders.CitySearchViewHolder
import com.weather.startscreen.adapter.holders.ErrorViewHolder
import com.weather.startscreen.adapter.holders.LoadingViewHolder
import com.weather.startscreen.adapter.holders.NoMatchViewHolder
import com.weather.startscreen.models.CityPres

class CitySearchAdapter(
    private val citySelectListener: (CityPres) -> Unit,
) : ListAdapter<CityAdapterInfo, RecyclerView.ViewHolder>(CityAdapterDiffUtils()) {

    private val items = mutableListOf<CityAdapterInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CityAdapterInfo.CITY_INFO_VIEW_TYPE -> CitySearchViewHolder(parent)
            CityAdapterInfo.LOADING_VIEW_TYPE -> LoadingViewHolder(parent)
            CityAdapterInfo.NO_MATCH_VIEW_TYPE -> NoMatchViewHolder(parent)
            CityAdapterInfo.ERROR_VIEW_TYPE -> ErrorViewHolder(parent)
            else -> throw RuntimeException("No view type")
        }
    }

    override fun submitList(list: MutableList<CityAdapterInfo>?) {
        super.submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CityAdapterInfo.CityInfo -> {
                (holder as CitySearchViewHolder).bind(item.city, citySelectListener)
            }
            is CityAdapterInfo.Loading -> {
                holder as LoadingViewHolder
            }
            is CityAdapterInfo.NoMatch -> {
                holder as NoMatchViewHolder
            }
            is CityAdapterInfo.Error -> {
                holder as ErrorViewHolder
            }
        }
    }

    fun getFilteredItemCount(): Int {
        return items.filterIsInstance<CityAdapterInfo.CityInfo>().size
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

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

}
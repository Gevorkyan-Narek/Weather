package com.weather.main.screen.city.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.city.adapter.model.CityInfoItemPres
import com.weather.main.screen.city.adapter.viewholders.*

class CitySelectAdapter(
    private val onSavedCitySelect: (CityInfoItemPres) -> Unit,
    private val onNewCitySelect: (CityInfoItemPres) -> Unit,
    private val onDeleteClick: (CityInfoItemPres) -> Unit,
) : ListAdapter<CityAdapterInfo, RecyclerView.ViewHolder>(CitySelectAdapterDiffUtils()) {

    private val items = mutableListOf<CityAdapterInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CityAdapterInfo.CITY_VIEW_TYPE -> CityViewHolder(parent)
            CityAdapterInfo.LOADING_VIEW_TYPE -> LoadingViewHolder(parent)
            CityAdapterInfo.NO_MATCH_VIEW_TYPE -> NoMatchViewHolder(parent)
            CityAdapterInfo.NEW_CITY_VIEW_TYPE -> NewCitySelectViewHolder(parent)
            CityAdapterInfo.ERROR_VIEW_TYPE -> ErrorViewHolder(parent)
            else -> throw RuntimeException("No view type")
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CityAdapterInfo.NewCityInfo -> {
                (holder as NewCitySelectViewHolder).bind(item.city, onNewCitySelect)
            }
            is CityAdapterInfo.Loading -> {
                holder as LoadingViewHolder
            }
            is CityAdapterInfo.NoMatch -> {
                holder as NoMatchViewHolder
            }
            is CityAdapterInfo.CityInfo -> {
                (holder as CityViewHolder).bind(
                    item.city,
                    onSavedCitySelect,
                    onDeleteClick = { deleteClick(item) }
                )
            }
            is CityAdapterInfo.Error -> {
                holder as ErrorViewHolder
            }
        }
    }

    override fun submitList(list: MutableList<CityAdapterInfo>?) {
        super.submitList(items)
    }

    fun getFilteredItemCount(): Int {
        return items.filterIsInstance<CityAdapterInfo.NewCityInfo>().size
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

    private fun deleteClick(item: CityAdapterInfo.CityInfo) {
        if (items.size > 1) {
            val position = items.indexOf(item)
            items.remove(item)
            notifyItemRemoved(position)
        }
        onDeleteClick(item.city)
    }

    fun clear() {
        if (items.isNotEmpty()) {
            val size = items.size
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

}
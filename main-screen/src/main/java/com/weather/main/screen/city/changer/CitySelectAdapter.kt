package com.weather.main.screen.city.changer

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.city.changer.model.CityInfoItemPres
import com.weather.main.screen.city.changer.viewholders.CityViewHolder
import com.weather.main.screen.city.changer.viewholders.LoadingViewHolder
import com.weather.main.screen.city.changer.viewholders.NewCitySelectViewHolder
import com.weather.main.screen.city.changer.viewholders.NoMatchViewHolder

class CitySelectAdapter(
    private val onSavedCitySelect: (CityInfoItemPres) -> Unit,
    private val onNewCitySelect: (CityInfoItemPres) -> Unit,
    private val onDeleteClick: (CityInfoItemPres) -> Unit,
) : ListAdapter<CityAdapterInfo, RecyclerView.ViewHolder>(CitySelectAdapterDiffUtils()) {

    private val savedItems = mutableListOf<CityAdapterInfo.CityInfo>()
    private val items = mutableListOf<CityAdapterInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CityAdapterInfo.CITY_VIEW_TYPE -> {
                CityViewHolder(parent)
            }
            CityAdapterInfo.LOADING_VIEW_TYPE -> {
                LoadingViewHolder(parent)
            }
            CityAdapterInfo.NO_MATCH_VIEW_TYPE -> {
                NoMatchViewHolder(parent)
            }
            CityAdapterInfo.NEW_CITY_VIEW_TYPE -> {
                NewCitySelectViewHolder(parent)
            }
            else -> {
                throw RuntimeException("No view type")
            }
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
                    onDeleteClick = { deleteClick(item, position) }
                )
            }
        }
    }

    override fun submitList(list: MutableList<CityAdapterInfo>?) {
        super.submitList(items)
    }

    fun updateItems(list: List<CityAdapterInfo>) {
        when {
            items.all { info -> info is CityAdapterInfo.Loading } && list.isEmpty() -> {
                items.clear()
                notifyItemRemoved(1)
                items.add(CityAdapterInfo.NoMatch)
                notifyItemInserted(items.size)
            }
            else -> {
                items.removeIf { item -> item is CityAdapterInfo.Loading }.apply {
                    if (this) {
                        notifyItemRemoved(items.size + 1)
                    }
                }
                val tempSize = items.size
                items.addAll(list)
                notifyItemRangeInserted(tempSize, list.size)
            }
        }
    }

    fun setSavedItems(list: List<CityAdapterInfo.CityInfo>) {
        if (savedItems != list) {
            savedItems.clear()
            savedItems.addAll(list)
            showSavedCities()
        }
    }

    fun clear() {
        if (items.isNotEmpty()) {
            val size = items.size
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    fun showSavedCities() {
        clear()
        items.addAll(savedItems)
        notifyItemRangeInserted(0, savedItems.size)
    }

    fun addLoading() {
        if (!items.contains(CityAdapterInfo.Loading) && savedItems != items) {
            items.add(CityAdapterInfo.Loading)
            notifyItemInserted(items.size)
        }
    }

    private fun deleteClick(item: CityAdapterInfo.CityInfo, position: Int) {
        if (items.size > 1) {
            savedItems.remove(item)
            items.remove(item)
            notifyItemRemoved(position)
        }
        onDeleteClick(item.city)
    }

}
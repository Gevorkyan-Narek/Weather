package com.weather.main.screen.main

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.city.adapter.CitySelectAdapter

class SuggestionsOnScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onStateChanged: ((RecyclerView, Int) -> Unit)? = null,
    private val onScrolledListener: ((offset: Int) -> Unit)? = null,
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        onStateChanged?.invoke(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visiblePos = layoutManager.findLastVisibleItemPosition()
        recyclerView.adapter?.let { adapter ->
            adapter as CitySelectAdapter
            if (visiblePos >= adapter.itemCount - 5) {
                onScrolledListener?.invoke(adapter.getFilteredItemCount())
            }
        }
    }
}
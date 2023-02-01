package com.weather.startscreen.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SuggestionsOnScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onStateChanged: ((RecyclerView, Int) -> Unit)? = null,
    private val onScrolledListener: ((RecyclerView, dx: Int, dy: Int) -> Unit)? = null,
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        onStateChanged?.invoke(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visiblePos = layoutManager.findLastVisibleItemPosition()
        recyclerView.adapter?.let { adapter ->
            if (visiblePos >= adapter.itemCount - 3) {
                onScrolledListener?.invoke(recyclerView, dx, dy)
            }
        }
    }
}
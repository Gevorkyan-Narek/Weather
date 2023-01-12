package com.weather.startscreen.adapter

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class CityAdapterItemDecoration(private val divider: Drawable) : RecyclerView.ItemDecoration() {

    private companion object {
        private const val START_INDEX = 0
        private const val LAST_TWO_DIVIDERS = 2
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft: Int = parent.paddingLeft
        val dividerRight: Int = parent.width - parent.paddingRight

        val childCount: Int = parent.childCount
        for (i in START_INDEX..childCount - LAST_TWO_DIVIDERS) {
            val child: View = parent.getChildAt(i)
            val dividerTop: Int =
                child.bottom + (child.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val dividerBottom: Int = dividerTop + divider.intrinsicHeight
            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }

}
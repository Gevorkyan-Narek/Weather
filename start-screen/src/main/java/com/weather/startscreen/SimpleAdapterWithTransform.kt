package com.weather.startscreen

import android.content.Context
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SimpleAdapterWithTransform<T, V>(
    context: Context,
    @LayoutRes resource: Int,
    private val mapper: (T) -> V,
) : ArrayAdapter<V>(context, resource, arrayListOf()) {

    private val logger: Logger = LoggerFactory.getLogger(SimpleAdapterWithTransform::class.java)

    fun submitList(list: List<T>) {
        clear()
        list.map(mapper).apply {
            logger.debug("before: $list\nafter: $this")
            addAll(this)
        }
    }

}
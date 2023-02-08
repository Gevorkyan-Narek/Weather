package com.weather.main.screen.city.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.databinding.NoMatchItemBinding

private fun inflateBinding(parent: ViewGroup) = NoMatchItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class NoMatchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(inflateBinding(parent).root)
package com.weather.startscreen.adapter.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.startscreen.databinding.NoMatchItemBinding

private fun inflateBinding(parent: ViewGroup) = NoMatchItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class NoMatchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(inflateBinding(parent).root)
package com.weather.main.screen.city.changer.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.main.screen.databinding.NoMatchItemBinding

private fun inflateBinding(parent: ViewGroup) = NoMatchItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class NoMatchViewHolder(
    parent: ViewGroup,
    binding: NoMatchItemBinding = inflateBinding(parent),
) : RecyclerView.ViewHolder(binding.root)
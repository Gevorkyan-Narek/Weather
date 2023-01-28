package com.weather.main.screen.city.changer.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.shared.styles.databinding.LoadingItemBinding

private fun inflateBinding(parent: ViewGroup) = LoadingItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class LoadingViewHolder(
    parent: ViewGroup,
    binding: LoadingItemBinding = inflateBinding(parent),
) : RecyclerView.ViewHolder(binding.root)
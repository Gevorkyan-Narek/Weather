package com.weather.startscreen.adapter.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.shared.styles.databinding.ErrorItemBinding

private fun inflateBinding(parent: ViewGroup) = ErrorItemBinding.inflate(
    LayoutInflater.from(parent.context),
    parent,
    false
)

class ErrorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(inflateBinding(parent).root)
package com.weather.custom.views.downloadstate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.weather.android.utils.getDrawable
import com.weather.android.utils.getString
import com.weather.custom.views.R
import com.weather.custom.views.databinding.DownloadStateLayoutBinding

class DownloadStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: DownloadStateLayoutBinding by lazy {
        DownloadStateLayoutBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    private var errorClickListener: OnClickListener? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.DownloadStateView,
            defStyleAttr,
            defStyleRes
        ).apply {
            val state = DownloadStatePres.valueOf(
                getInteger(R.styleable.DownloadStateView_downloadState, 0)
            )
            downloadState = state
            recycle()
        }
    }

    var downloadState: DownloadStatePres = DownloadStatePres.SUCCESS
        set(value) {
            updateDownloadStateView(value)
            field = value
        }

    private fun updateDownloadStateView(state: DownloadStatePres) {
        binding.run {
            when (state) {
                DownloadStatePres.SUCCESS -> {
                    root.isVisible = false
                }
                DownloadStatePres.ERROR -> {
                    root.isVisible = true
                    alertTextView.text = getString(R.string.please_try_again)
                    icon.setImageDrawable(getDrawable(context, R.drawable.ic_alert))
                    root.setOnClickListener(errorClickListener)
                }
                DownloadStatePres.LOADING -> {
                    root.isVisible = true
                    alertTextView.text = getString(R.string.loading)
                    icon.setImageDrawable(getDrawable(context, R.drawable.ic_warning))
                    root.setOnClickListener(null)
                }
            }
        }
    }

    fun setOnErrorClickListener(listener: OnClickListener) {
        errorClickListener = listener
    }

}
package com.weather.custom.views.weatherfield

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.weather.android.utils.emptyString
import com.weather.custom.views.R
import com.weather.custom.views.databinding.WeatherFieldBinding

class WeatherField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding by lazy {
        WeatherFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.WeatherField,
            defStyleAttr,
            defStyleRes
        ).apply {
            fieldLabel = getString(R.styleable.WeatherField_fieldLabel).orEmpty()
            val value = getString(R.styleable.WeatherField_fieldValue).orEmpty()
            val unit = WeatherFieldUnitEnum.valueOf(getInteger(R.styleable.WeatherField_unit, 0))

            setFieldValue(value, unit)
            recycle()
        }
    }

    var fieldLabel: String = emptyString()
        set(value) {
            binding.label.text = resources.getString(R.string.label, value)
            field = value
        }

    fun setFieldValue(text: String, unit: WeatherFieldUnitEnum) {
        binding.value.text = resources.getString(
            R.string.value,
            text, resources.getString(unit.unit)
        )
    }

}
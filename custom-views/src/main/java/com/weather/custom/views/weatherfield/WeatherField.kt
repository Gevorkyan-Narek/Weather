package com.weather.custom.views.weatherfield

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.weather.base.utils.emptyString
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
            weatherIcon = getInteger(R.styleable.WeatherField_weatherIcon, 0).takeIf { it != 0 }

            val value = getString(R.styleable.WeatherField_fieldValue).orEmpty()
            val unit = WeatherFieldUnitEnum.valueOf(getInteger(R.styleable.WeatherField_unit, 0))

            setFieldValue(value, unit)
            recycle()
        }
    }

    var fieldLabel: String = emptyString()
        set(value) {
            binding.label.text = value
            field = value
        }

    @DrawableRes
    var weatherIcon: Int? = null
        set(value) {
            if (value != null)
                binding.icon.setImageDrawable(ContextCompat.getDrawable(context, value))
        }

    fun setFieldValue(text: String, unit: WeatherFieldUnitEnum): String {
        return text + ":" + resources.getString(unit.unit)
    }

}
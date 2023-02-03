package com.weather.main.screen.today

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.custom.views.weatherfield.WeatherFieldUnitEnum
import com.weather.main.screen.R
import com.weather.main.screen.databinding.FTodayScreenBinding
import com.weather.main.screen.model.WeatherMetricsPres
import com.weather.main.screen.model.WeatherPres
import com.weather.main.screen.today.adapter.TodayTimeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class TodayFragment : BindingFragment<FTodayScreenBinding>() {

    companion object {
        private const val ONE_KM = 1000f
    }

    private val viewModel: TodayViewModel by viewModel()

    private val adapter by lazy {
        TodayTimeAdapter(viewModel::timeClicked)
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FTodayScreenBinding = FTodayScreenBinding.inflate(inflater, container, false)

    override fun FTodayScreenBinding.initView() {
        byTimeRecycler.adapter = adapter
        adapter.submitList(null)
    }

    override fun FTodayScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(todayDate) { dateString ->
                date.text = dateString
            }
            observe(currentWeatherLiveData, ::handleCurrentForecast)
            observe(todayForecastLiveData, ::handleTodayForecast)
            observe(detailLiveData, ::handleDetails)
        }
    }

    private fun handleCurrentForecast(pres: WeatherPres) {
        binding?.run {
            temp.text = getString(R.string.tempWithoutCelsius, pres.metrics.temp)
            pres.shortInfo?.let { shortInfo ->
                if (shortInfo.icon != null) {
                    weatherImage.setImageResource(shortInfo.icon)
                }
                tempDescription.text = shortInfo.description
            }
            with(pres.metrics) {
                feelsLikeTemp.text = getString(R.string.tempWithCelsius, feelsLike)
                precipitationField.setFieldValue(pop.toString(), WeatherFieldUnitEnum.PERCENT)
                humidityField.setFieldValue(humidity.toString(), WeatherFieldUnitEnum.PERCENT)
                cloudinessField.setFieldValue(
                    cloudiness.toString(), WeatherFieldUnitEnum.PERCENT
                )
            }
            with(pres.wind) {
                degreeValue.text = getString(R.string.degree, degree)
                windField.setFieldValue(gust.toString(), WeatherFieldUnitEnum.SPEED)
            }
        }
    }

    private fun handleTodayForecast(pres: List<WeatherPres>) {
        binding?.run {
            val max =
                pres.map(WeatherPres::metrics).map(WeatherMetricsPres::temp).maxBy { abs(it) }
            val min =
                pres.map(WeatherPres::metrics).map(WeatherMetricsPres::temp).minBy { abs(it) }
            tempMinMax.text = getString(R.string.tempMaxMin, max, min)
            adapter.updateItems(pres)
        }
    }

    private fun handleDetails(pres: WeatherPres) {
        binding?.run {
            with(pres.metrics) {
                detailsFeelsLikeValue.text = getString(R.string.tempWithCelsius, feelsLike)
                detailsHumidityValue.text = humidity.toString()
                detailsVisibilityValue.text = if (visibility > ONE_KM) {
                    getString(R.string.visibilityKilometers, visibility / ONE_KM)
                } else {
                    getString(R.string.visibilityMeters, visibility)
                }
                detailsCloudinessValue.text = getString(R.string.withPercent, cloudiness)
            }
            pres.shortInfo?.icon?.run {
                detailImage.setImageResource(this)
            }
        }
    }

}
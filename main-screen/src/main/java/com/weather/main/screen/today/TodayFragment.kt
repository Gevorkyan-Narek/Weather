package com.weather.main.screen.today

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.observe
import com.weather.android.utils.setWeatherIcon
import com.weather.custom.views.weatherfield.WeatherFieldUnitEnum
import com.weather.main.screen.R
import com.weather.main.screen.databinding.TodayScreenBinding
import com.weather.main.screen.model.WeatherMetricsPres
import com.weather.main.screen.model.WeatherPres
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.slf4j.LoggerFactory
import kotlin.math.abs

class TodayFragment : BindingFragmentMVVM<TodayScreenBinding>() {

    private val viewModel: TodayViewModel by viewModel()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): TodayScreenBinding = TodayScreenBinding.inflate(inflater, container, false)

    override fun TodayScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(todayDate) { dateString ->
                date.text = dateString
            }
            observe(currentForecastLiveData) { pres ->
                LoggerFactory.getLogger(javaClass).debug("current: $pres")
                temp.text = getString(R.string.tempWithoutCelsius, pres.metrics.temp)
                setWeatherIcon(pres.shortInfo.first().icon, weatherImage)
                tempDescription.text = pres.shortInfo.joinToString { info -> info.description }
                with(pres.metrics) {
                    feelsLikeTemp.text = getString(R.string.tempWithCelsius, feelsLike)
                    precipitationField.setFieldValue(pop.toString(), WeatherFieldUnitEnum.PERCENT)
                    humidityField.setFieldValue(humidity.toString(), WeatherFieldUnitEnum.PERCENT)
                }
                with(pres.wind) {
                    windSpeed.text = getString(R.string.windSpeed, speed)
                    degreeValue.text = getString(R.string.degree, degree)
                    windField.setFieldValue(gust.toString(), WeatherFieldUnitEnum.SPEED)
                }
            }
            observe(todayForecastLiveData) { pres ->
                LoggerFactory.getLogger(javaClass).debug("fullToday: $pres")
                val max =
                    pres.map(WeatherPres::metrics).map(WeatherMetricsPres::temp).maxBy { abs(it) }
                val min =
                    pres.map(WeatherPres::metrics).map(WeatherMetricsPres::temp).minBy { abs(it) }
                tempMinMax.text = getString(R.string.tempMinMax, max, min)
            }
        }
    }

}
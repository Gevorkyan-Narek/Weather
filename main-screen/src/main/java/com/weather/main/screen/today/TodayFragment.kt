package com.weather.main.screen.today

import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.observe
import com.weather.custom.views.weatherfield.WeatherFieldUnitEnum
import com.weather.main.screen.R
import com.weather.main.screen.databinding.TodayScreenBinding
import com.weather.main.screen.model.WeatherPres
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.slf4j.LoggerFactory

class TodayFragment : BindingFragmentMVVM<TodayScreenBinding>() {

    private val viewModel: TodayViewModel by viewModel()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): TodayScreenBinding = TodayScreenBinding.inflate(inflater, container, false)

    override fun TodayScreenBinding.initView() {

    }

    override fun TodayScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(todayDate) { dateString ->
                date.text = dateString
            }
            observe(currentForecastLiveData) { pres ->
                LoggerFactory.getLogger(javaClass).debug("current: $pres")
                temp.text = getString(R.string.tempWithoutCelsius, pres.metrics.temp)
                tempDescription.text = pres.weatherDescription.joinToString()
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
                val max = pres.map(WeatherPres::metrics).maxBy { metrics -> metrics.tempMax }
                val min = pres.map(WeatherPres::metrics).maxBy { metrics -> metrics.tempMin }
                tempMinMax.text = getString(R.string.tempMinMax, max.tempMax, min.tempMin)
            }
        }
    }

}
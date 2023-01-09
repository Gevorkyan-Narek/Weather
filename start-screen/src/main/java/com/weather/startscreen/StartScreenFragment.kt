package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.fragment.observe
import com.weather.startscreen.databinding.FStartScreenBinding
import com.weather.startscreen.models.CityPres
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

    private val viewModel: StartScreenViewModel by viewModel()

    private val adapter by lazy {
        SimpleAdapterWithTransform<CityPres, String>(
            context = requireContext(),
            resource = R.layout.support_simple_spinner_dropdown_item,
            mapper = { pres -> pres.name }
        )
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FStartScreenBinding = FStartScreenBinding.inflate(inflater, container, false)

    override fun FStartScreenBinding.initView() {
        cityEditText.setAdapter(adapter)
        cityEditText.addTextChangedListener { editable ->
            viewModel.onCityTextChanged(editable?.trim().toString())
        }
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(motionStartEvent) {
                motionLayout.transitionToEnd()
            }
            observe(matchCitiesLiveData) { pres ->
                adapter.submitList(pres.data)
                adapter.filter.filter(cityEditText.text, cityEditText)
            }
        }
    }
}
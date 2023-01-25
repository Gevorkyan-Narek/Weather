package com.weather.main.screen.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.weather.android.utils.observe
import com.weather.main.screen.databinding.CityMenuBinding
import com.weather.main.screen.dialog.adapter.CitiesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityDialog : DialogFragment() {

    private var binding: CityMenuBinding? = null

    private val viewModel: CityDialogViewModel by viewModel()

    private val citiesAdapter: CitiesAdapter by lazy {
        CitiesAdapter(viewModel::citySelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CityMenuBinding.inflate(inflater, container, false)
        binding?.run {
            cityRecycler.adapter = citiesAdapter
            addCity.setOnClickListener {
                viewModel.addCity()
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.run {
            observe(cities) { cities ->
                citiesAdapter.submitList(cities)
            }
            observe(dismissLiveData) {
                dismiss()
            }
        }
    }

}
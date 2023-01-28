package com.weather.main.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.android.utils.setupWithViewPager2
import com.weather.android.utils.toBottomSheetBehaviour
import com.weather.main.screen.city.changer.CitySelectAdapter
import com.weather.main.screen.databinding.MainScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : BindingFragment<MainScreenBinding>() {

    private val viewModel: MainScreenViewModel by viewModel()

    private val linearLayoutManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val scrollListener by lazy {
        SuggestionsOnScrollListener(
            linearLayoutManager,
            onScrolledListener = { _, _, _ ->
                viewModel.onScrolled()
            }
        )
    }

    private val citySelectAdapter by lazy {
        CitySelectAdapter(
            viewModel::onCitySelect,
            viewModel::onNewCitySelect
        )
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): MainScreenBinding = MainScreenBinding.inflate(inflater, container, false)

    override fun MainScreenBinding.initView() {
        viewPager.adapter = MainScreenFragmentPagerAdapter(childFragmentManager, lifecycle)
        tabLayout.setupWithViewPager2(
            viewPager,
            WeatherFragmentsEnum.values().map { title -> getString(title.titleId) }
        )
        location.setOnClickListener {
            viewModel.locationClicked()
        }
        bottomSheet.toBottomSheetBehaviour().state = BottomSheetBehavior.STATE_HIDDEN
        suggestionsRecycler.layoutManager = linearLayoutManager
        suggestionsRecycler.addOnScrollListener(scrollListener)
        suggestionsRecycler.adapter = citySelectAdapter
        citySelectAdapter.submitList(null)
        cityEditText.addTextChangedListener {
            viewModel.onCityPrefixChanged(it?.toString().orEmpty())
        }
    }

    override fun MainScreenBinding.observeViewModel() {
        viewModel.run {
            observe(stateBottomSheetLiveData) { state ->
                bottomSheet.toBottomSheetBehaviour().state = state
                if (state == BottomSheetBehavior.STATE_HIDDEN) {
                    citySelectAdapter.showSavedCities()
                }
            }
            observe(cityTitle) { cityName ->
                title.text = cityName
            }
            observe(loadingLiveData) {
                citySelectAdapter.addLoading()
            }
            observe(savedCities) { list ->
                citySelectAdapter.setSavedItems(list)
            }
            observe(downloadCities) { list ->
                citySelectAdapter.updateItems(list)
            }
            observe(cityPrefixChangedLiveData) { isBlank ->
                if (isBlank) {
                    citySelectAdapter.showSavedCities()
                } else {
                    citySelectAdapter.clear()
                }
            }
        }
    }

}
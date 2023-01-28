package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.startscreen.adapter.SuggestionsOnScrollListener
import com.weather.startscreen.databinding.FStartScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragment<FStartScreenBinding>() {

    companion object {
        private const val MOTION_DELAY = 1500L
    }

    private val viewModel: StartScreenViewModel by viewModel()

    private val citySearchAdapter by lazy { CitySearchAdapter(viewModel::onCitySelect) }

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

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FStartScreenBinding = FStartScreenBinding.inflate(inflater, container, false)

    override fun FStartScreenBinding.initView() {
        cityEditText.addTextChangedListener { editable ->
            viewModel.onCityTextChanged(editable?.trim().toString())
        }
        with(suggestionsRecycler) {
            layoutManager = linearLayoutManager
            adapter = citySearchAdapter
            citySearchAdapter.submitList(null)
            addOnScrollListener(scrollListener)
//            addItemDecoration(
//                CityAdapterItemDecoration(getDrawable(requireContext(), R.drawable.line))
//            )
        }
        lifecycleScope.launch {
            delay(MOTION_DELAY)
            motionLayout.setTransition(R.id.startTransition)
            motionLayout.transitionToEnd()
        }
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(searchList, citySearchAdapter::updateItems)
            observe(motionEvent) { isBlank ->
                if (isBlank) {
                    motionLayout.setTransition(R.id.emptySearchTransition)
                } else {
                    motionLayout.setTransition(R.id.chooseCityTransition)
                }
                motionLayout.transitionToEnd()
            }
            observe(loadingLiveData) {
                citySearchAdapter.addLoading()
            }
            observe(clearSearchListEvent) {
                citySearchAdapter.clear()
            }
            observe(navigationEvent) {
                findNavController().navigate(R.id.fromStartToWeatherScreen)
            }
        }
    }
}
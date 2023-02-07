package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.android.utils.fragment.BindingFragment
import com.weather.android.utils.observe
import com.weather.startscreen.adapter.SuggestionsOnScrollListener
import com.weather.startscreen.databinding.FStartScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragment<FStartScreenBinding>() {

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
            onScrolledListener = { offset ->
                viewModel.onScrolled(offset)
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
        }
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(searchList, citySearchAdapter::updateItems)
            observe(loadMoreCitiesList, citySearchAdapter::loadMore)
            observe(motionStartEvent) {
                motionLayout.setTransition(R.id.startTransition)
                motionLayout.transitionToEnd()
            }
            observe(motionEvent) { isBlank ->
                if (isBlank) {
                    motionLayout.setTransition(R.id.emptySearchTransition)
                } else {
                    motionLayout.setTransition(R.id.chooseCityTransition)
                }
                motionLayout.transitionToEnd()
            }
            observe(clearSearchList) {
                citySearchAdapter.clear()
            }
            observe(addLoading) {
                citySearchAdapter.addLoading()
            }
            observe(navigationEvent) { navigationInfo ->
                val navOption = NavOptions.Builder()
                    .setPopUpTo(navigationInfo.popupTo, navigationInfo.inclusive)
                    .build()

                findNavController().navigate(
                    resId = navigationInfo.actionId,
                    navOptions = navOption,
                    args = bundleOf()
                )
            }
        }
    }


}
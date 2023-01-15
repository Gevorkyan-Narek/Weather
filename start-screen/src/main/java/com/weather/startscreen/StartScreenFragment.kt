package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.getDrawable
import com.weather.android.utils.observe
import com.weather.startscreen.adapter.CityAdapterItemDecoration
import com.weather.startscreen.databinding.FStartScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visiblePos = linearLayoutManager.findLastVisibleItemPosition()
            recyclerView.adapter?.let { adapter ->
                if (visiblePos >= adapter.itemCount - 1) {
                    viewModel.onScrolled()
                }
            }
        }
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
            addOnScrollListener(scrollListener)
            addItemDecoration(
                CityAdapterItemDecoration(getDrawable(requireContext(), R.drawable.line))
            )
        }
        lifecycleScope.launch {
            delay(MOTION_DELAY)
            motionLayout.setTransition(R.id.startTransition)
            motionLayout.transitionToEnd()
        }
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(motionEvent) { isNotBlank ->
                if (isNotBlank) {
                    motionLayout.setTransition(R.id.chooseCityTransition)
                } else {
                    motionLayout.setTransition(R.id.emptySearchTransition)
                }
                motionLayout.transitionToEnd()
            }
            observe(searchList) { list ->
                citySearchAdapter.submitList(list)
            }
            observe(navigationEvent) {
                findNavController().navigate(R.id.fromStartToWeatherScreen)
            }
        }
    }
}
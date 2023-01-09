package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.fragment.observe
import com.weather.startscreen.databinding.FStartScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

    private val viewModel: StartScreenViewModel by viewModel()

    private val adapter = CitySearchAdapter()

    private val layoutManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visiblePos = layoutManager.findLastVisibleItemPosition()
            logger.info("visiblePos: $visiblePos, itemCount: ${adapter.itemCount}")
            recyclerView.adapter?.let { adapter ->
                if (visiblePos >= adapter.itemCount - 3) {
                    logger.debug("$visiblePos/${adapter.itemCount}")
                    viewModel.onScrolled()
                }
            }
        }
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FStartScreenBinding = FStartScreenBinding.inflate(inflater, container, false)

    override fun FStartScreenBinding.initView() {
        cityEditText.addTextChangedListener { editable ->
            viewModel.onCityTextChanged(editable?.trim().toString())
        }
        suggestionsRecycler.layoutManager = layoutManager
        suggestionsRecycler.adapter = adapter
        suggestionsRecycler.addOnScrollListener(scrollListener)
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(motionStartEvent) {
                motionLayout.setTransition(R.id.startTransition)
                motionLayout.transitionToEnd()
            }
            observe(matchCitiesLiveData) { pres ->
                adapter.submitList(pres)
            }
            observe(emptySearchLiveData) { isSearching ->
                if (isSearching) {
                    motionLayout.setTransition(R.id.chooseCityTransition)
                } else {
                    motionLayout.setTransition(R.id.emptySearchTransition)
                }
                motionLayout.transitionToEnd()
            }
            observe(insertNewCitiesLiveData) { pres ->
                adapter.addCities(pres)
            }
        }
    }
}
package com.weather.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.android.utils.fragment.BindingFragmentMVVM
import com.weather.android.utils.getDrawable
import com.weather.android.utils.observe
import com.weather.startscreen.adapter.CityAdapterItemDecoration
import com.weather.startscreen.databinding.FStartScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

    private val viewModel: StartScreenViewModel by viewModel()

    private val adapter by lazy { CitySearchAdapter(viewModel::onCitySelect) }

    private val layoutManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visiblePos = layoutManager.findLastVisibleItemPosition()
            recyclerView.adapter?.let { adapter ->
                if (adapter.itemCount > 9 && visiblePos >= adapter.itemCount - 1) {
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
        suggestionsRecycler.layoutManager = layoutManager
        suggestionsRecycler.adapter = adapter
        suggestionsRecycler.addOnScrollListener(scrollListener)
        suggestionsRecycler.addItemDecoration(
            CityAdapterItemDecoration(getDrawable(requireContext(), R.drawable.line))
        )
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(motionStartEvent) {
                motionLayout.setTransition(R.id.startTransition)
                motionLayout.transitionToEnd()
            }
            observe(emptySearchLiveData) { isSearching ->
                if (isSearching) {
                    motionLayout.setTransition(R.id.chooseCityTransition)
                } else {
                    motionLayout.setTransition(R.id.emptySearchTransition)
                }
                motionLayout.transitionToEnd()
            }
            observe(searchList) { list ->
                adapter.submitList(list)
            }
            observe(navigationEvent) {
                findNavController().navigate(R.id.fromStartToWeatherScreen)
            }
        }
    }
}
package com.weather.main.screen.main.bottom.sheet

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weather.android.utils.observe
import com.weather.main.screen.R
import com.weather.main.screen.city.changer.CitySelectAdapter
import com.weather.main.screen.databinding.FCityBottomSheetBinding
import com.weather.main.screen.main.SuggestionsOnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FCityBottomSheetBinding? = null

    private val viewModel: CityBottomSheetViewModel by viewModel()

    private val linearLayoutManager by lazy {
        LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private val citySelectAdapter by lazy {
        CitySelectAdapter(
            viewModel::onCitySelect,
            viewModel::onNewCitySelect,
            viewModel::onCityDelete
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FCityBottomSheetBinding.inflate(inflater, container, false)
        initView()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun initView() {
        binding?.run {
            root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
            suggestionsRecycler.layoutManager = linearLayoutManager
            suggestionsRecycler.addOnScrollListener(scrollListener)
            suggestionsRecycler.adapter = citySelectAdapter
            citySelectAdapter.submitList(null)
            cityEditText.addTextChangedListener { editable ->
                viewModel.onCityPrefixChanged(editable?.toString().orEmpty())
            }
        }
    }


    private fun observeViewModel() {
        viewModel.run {
            observe(popBackStackEvent) {
                findNavController().popBackStack()
            }
            observe(clearSearchList) {
                citySelectAdapter.clear()
            }
            observe(addLoading) {
                citySelectAdapter.addLoading()
            }
            observe(loadMoreCitiesList) { list ->
                citySelectAdapter.loadMore(list)
            }
            observe(searchList) { list ->
                citySelectAdapter.updateItems(list)
            }
            observe(showSavedCities) { isShow ->
                citySelectAdapter.showSavedCities(isShow)
            }
            observe(savedCities) { list ->
                citySelectAdapter.setSavedItems(list)
            }
            observe(showDeleteWarning) {
                Toast.makeText(context, R.string.deleteWarning, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
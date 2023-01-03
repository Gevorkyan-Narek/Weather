package com.example.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.android.utils.fragment.BindingFragmentMVVM
import com.example.android.utils.fragment.observe
import com.example.startscreen.databinding.FStartScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

    private val viewModel: StartScreenViewModel by viewModel()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FStartScreenBinding = FStartScreenBinding.inflate(inflater, container, false)

    override fun FStartScreenBinding.initView() {
        cityEditText.addTextChangedListener { editable ->
            viewModel.onCityTextChanged(editable?.trim().toString())
        }
    }

    override fun FStartScreenBinding.observeViewModel() {
        with(viewModel) {
            observe(motionStartEvent) {
                motionLayout.setTransitionDuration(R.dimen.motionSceneDuration)
                motionLayout.transitionToEnd()
            }
        }
    }
}
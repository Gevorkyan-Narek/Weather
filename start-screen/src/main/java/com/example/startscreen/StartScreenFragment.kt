package com.example.startscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.android.utils.fragment.BindingFragmentMVVM
import com.example.startscreen.databinding.FStartScreenBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartScreenFragment : BindingFragmentMVVM<FStartScreenBinding>() {

    companion object {
        private const val ONE_SEC_DURATION = 1000
    }

    private val viewModel: StartScreenViewModel by viewModel()

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FStartScreenBinding = FStartScreenBinding.inflate(inflater, container, false)

    override fun observeViewModel() {
        viewModel.motionEvent.observe(this) {
            binding?.motionLayout?.setTransitionDuration(ONE_SEC_DURATION)
            binding?.motionLayout?.transitionToEnd()
        }
    }

}
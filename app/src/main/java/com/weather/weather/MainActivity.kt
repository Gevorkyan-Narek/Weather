package com.weather.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.android.utils.observe
import com.weather.weather.callback.BackPressedCallback
import com.weather.weather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val backPressedCallback by lazy { BackPressedCallback(viewModel::onBackPressed) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun observeViewModel() {
        viewModel.run {
            observe(backPressed) {
                finish()
            }
        }
    }

}
package com.close.svea.refactoringsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        val mainViewModel by viewModel<MainViewModel>()
//        val mainViewModel = getViewModel<MainViewModel>()

        val adapter = PlacesListAdapter()

        binding.mainViewModel = mainViewModel
        binding.placesRecyclerView.adapter = adapter
        binding.lifecycleOwner = this

        mainViewModel.placesLiveData.observe(
            this,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
    }
}

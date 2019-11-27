package com.close.svea.refactoringsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this
        val adapter = PlacesListAdapter()
        binding.placesRecyclerView.adapter = adapter

        mainViewModel.getPlacesLiveData().observe(this,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
    }
}

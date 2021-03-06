package com.close.svea.refactoringsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.base.BaseApplication.Companion.getAppInjector
import com.close.svea.refactoringsample.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAppInjector().inject(this)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        val mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
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

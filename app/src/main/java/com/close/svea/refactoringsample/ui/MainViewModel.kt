package com.close.svea.refactoringsample.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.base.BaseApplication
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var placesRepository: PlacesRepository
    var placesLiveData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        BaseApplication.appComponent.inject(this)
    }

    fun getAllPlaces() {
        if (isConnectedToNetwork(getApplication())) {
            isLoading.value = true
            viewModelScope.launch(Dispatchers.Main) {
                val result = placesRepository.getAllPlaces()
                if (result.isNotEmpty())
                    placesLiveData.value = result
                else
                    Toast.makeText(getApplication(), R.string.try_again, Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }
        } else
            Toast.makeText(getApplication(), R.string.not_connected, Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        super.onCleared()
        placesLiveData.value?.clear()
    }
}

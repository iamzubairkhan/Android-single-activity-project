package com.close.svea.refactoringsample.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val application: Application,
    private val placesRepository: PlacesRepository
) : ViewModel() {

    var placesLiveData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getAllPlaces() {
        if (isConnectedToNetwork(application)) {
            isLoading.value = true
            viewModelScope.launch(Dispatchers.Main) {
                val result = placesRepository.getAllPlaces()
                if (result.isNotEmpty())
                    placesLiveData.value = result
                else
                    Toast.makeText(application, R.string.try_again, Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }
        } else
            Toast.makeText(application, R.string.not_connected, Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        super.onCleared()
        placesLiveData.value?.clear()
    }
}

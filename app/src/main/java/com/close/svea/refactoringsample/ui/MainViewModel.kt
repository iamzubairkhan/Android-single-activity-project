package com.close.svea.refactoringsample.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val context: Context,
    private val placesRepository: PlacesRepository
) : ViewModel() {

    private val _placesLiveData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val placesLiveData: LiveData<MutableList<Place>>
        get() = _placesLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun getAllPlaces() {
        if (isConnectedToNetwork(context)) {
            _isLoadingLiveData.value = true
            viewModelScope.launch(Dispatchers.Main) {
                val result = placesRepository.getAllPlaces()
                if (result.isNotEmpty())
                    _placesLiveData.value = result
                else
                    Toast.makeText(context, R.string.try_again, Toast.LENGTH_SHORT).show()
                _isLoadingLiveData.value = false
            }
        } else
            Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        super.onCleared()
        _placesLiveData.value?.clear()
    }
}

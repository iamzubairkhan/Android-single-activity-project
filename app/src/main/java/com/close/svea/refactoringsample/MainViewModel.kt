package com.close.svea.refactoringsample

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var placesApiClient = PlacesApiClient()
    var message: MutableLiveData<String> = MutableLiveData("")
    private val resources = getApplication<Application>().resources

    fun searchPlaces() {
        if (isConnectedToNetwork(getApplication())) {
            placesApiClient.searchPlaces()
            message.value = resources.getString(R.string.connected)
        } else {
            Toast.makeText(getApplication(), R.string.not_connected, Toast.LENGTH_LONG).show()
            message.value = resources.getString(R.string.not_connected)
        }
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesApiClient.getPlacesLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        placesApiClient.getPlacesLiveData().value?.clear()
    }
}

package com.close.svea.refactoringsample.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.close.svea.refactoringsample.R
import com.close.svea.refactoringsample.base.BaseApplication
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var placesRepository: PlacesRepository
    var message: MutableLiveData<String> = MutableLiveData("")
    private val resources = getApplication<Application>().resources

    init {
        BaseApplication.appComponent.inject(this)
    }

    fun searchPlaces() {
        if (isConnectedToNetwork(getApplication())) {
            placesRepository.searchPlaces()
            message.value = resources.getString(R.string.connected)
        } else {
            Toast.makeText(getApplication(), R.string.not_connected, Toast.LENGTH_LONG).show()
            message.value = resources.getString(R.string.not_connected)
        }
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesRepository.getPlacesLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        placesRepository.getPlacesLiveData().value?.clear()
    }
}

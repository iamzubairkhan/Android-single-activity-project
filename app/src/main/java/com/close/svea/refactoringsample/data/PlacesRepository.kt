package com.close.svea.refactoringsample.data

import androidx.lifecycle.LiveData
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.data.rest.PlacesApiClient

class PlacesRepository {
    private var placesApiClient = PlacesApiClient()

    fun searchPlaces() {
        placesApiClient.searchPlaces()
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesApiClient.getPlacesLiveData()
    }
}
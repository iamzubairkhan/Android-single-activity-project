package com.close.svea.refactoringsample.data

import androidx.lifecycle.LiveData
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesApiClient: PlacesApiClient) {

    fun searchPlaces() {
        placesApiClient.searchPlaces()
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesApiClient.getPlacesLiveData()
    }
}
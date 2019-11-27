package com.close.svea.refactoringsample.data

import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val placesApiClient: PlacesApiClient) {

    suspend fun getAllPlaces(): MutableList<Place> {
        return placesApiClient.getAllPlaces()
    }
}
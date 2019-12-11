package com.close.svea.refactoringsample.data

import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.data.rest.PlacesApiClient

class PlacesRepository(private val placesApiClient: PlacesApiClient) {

    suspend fun getAllPlaces(): MutableList<Place> {
        return placesApiClient.getAllPlaces()
    }
}
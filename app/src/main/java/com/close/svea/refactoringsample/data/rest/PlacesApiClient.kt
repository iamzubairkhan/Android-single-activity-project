package com.close.svea.refactoringsample.data.rest

import com.close.svea.refactoringsample.data.model.Place

class PlacesApiClient(private val placesApiService: PlacesApiService) {

    suspend fun getAllPlaces(): MutableList<Place> {
        return placesApiService.getAllPlaces().place
    }
}
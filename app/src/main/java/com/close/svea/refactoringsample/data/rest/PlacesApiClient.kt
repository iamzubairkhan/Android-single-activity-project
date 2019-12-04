package com.close.svea.refactoringsample.data.rest

import com.close.svea.refactoringsample.data.model.Place
import javax.inject.Inject

class PlacesApiClient @Inject constructor(private val placesApiService: PlacesApiService) {

    suspend fun getAllPlaces(): MutableList<Place> {
        return placesApiService.getAllPlaces().place
    }
}
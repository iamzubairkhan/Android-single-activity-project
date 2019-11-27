package com.close.svea.refactoringsample.data.rest

import com.close.svea.refactoringsample.data.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesApiClient @Inject constructor(private val placesApiService: PlacesApiService) {

    suspend fun getAllPlaces(): MutableList<Place> {
        return withContext(Dispatchers.IO) {
            placesApiService.getAllPlaces().execute().body()!!.place
        }
    }
}
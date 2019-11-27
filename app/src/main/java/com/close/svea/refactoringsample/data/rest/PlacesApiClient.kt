package com.close.svea.refactoringsample.data.rest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.close.svea.refactoringsample.data.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlacesApiClient @Inject constructor(private val placesApiService: PlacesApiService) {

    private var placesLiveData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun searchPlaces() {
        val job = GlobalScope.async(Dispatchers.IO) {
            placesApiService.getAllPlaces().execute().body()!!.place
        }
        GlobalScope.launch(Dispatchers.Main) {
            placesLiveData.value = job.await()
        }
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesLiveData
    }
}
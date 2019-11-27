package com.close.svea.refactoringsample.data.rest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.utils.AppConstants.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlacesApiClient {

    private var placesLiveData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    private val restClient: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun searchPlaces() {
        val job = GlobalScope.async(Dispatchers.IO) {
            restClient.create(PlacesApiService::class.java).getAllPlaces().execute().body()!!.place
        }
        GlobalScope.launch(Dispatchers.Main) {
            placesLiveData.value = job.await()
        }
    }

    fun getPlacesLiveData(): LiveData<MutableList<Place>> {
        return placesLiveData
    }
}
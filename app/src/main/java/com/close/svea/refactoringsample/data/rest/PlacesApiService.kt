package com.close.svea.refactoringsample.data.rest

import com.close.svea.refactoringsample.data.model.Places
import com.close.svea.refactoringsample.utils.AppConstants.DEFAULT_APP_ID
import com.close.svea.refactoringsample.utils.AppConstants.DEFAULT_NO_RECORDS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {

    @GET("meappid")
    fun getAllPlaces(
        @Query("meAppId") id: Int = DEFAULT_APP_ID,
        @Query("records") records: Int = DEFAULT_NO_RECORDS
    ): Call<Places>
}

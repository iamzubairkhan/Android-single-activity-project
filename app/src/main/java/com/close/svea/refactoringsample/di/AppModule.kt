package com.close.svea.refactoringsample.di


import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import com.close.svea.refactoringsample.data.rest.PlacesApiService
import com.close.svea.refactoringsample.utils.AppConstants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class AppModule {

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePlacesApiService(retrofit: Retrofit): PlacesApiService {
        return retrofit.create(PlacesApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesPlacesApiClient(placesApiService: PlacesApiService): PlacesApiClient {
        return PlacesApiClient(placesApiService)
    }

    @Singleton
    @Provides
    fun providesPlacesRepository(placesApiClient: PlacesApiClient): PlacesRepository {
        return PlacesRepository(placesApiClient)
    }
}

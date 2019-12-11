package com.close.svea.refactoringsample.di

import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import com.close.svea.refactoringsample.data.rest.PlacesApiService
import com.close.svea.refactoringsample.ui.MainViewModel
import com.close.svea.refactoringsample.utils.AppConstants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun retrofitInstance(): Retrofit =
    Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private val placesApiService: PlacesApiService =
    retrofitInstance().create(PlacesApiService::class.java)

val appModule = module {
    single { placesApiService }
    single { PlacesApiClient(placesApiService = get()) }
    single { PlacesRepository(placesApiClient = get()) }
    viewModel { MainViewModel(androidContext(), placesRepository = get()) }
}

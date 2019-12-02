package com.close.svea.refactoringsample.data

import com.close.svea.refactoringsample.data.rest.PlacesApiClient
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

@ExperimentalCoroutinesApi
class PlacesRepositoryTest {

    private val placesApiClient: PlacesApiClient = mockk()
    private val placesRepository = PlacesRepository(placesApiClient)

    @Test
    fun `call to getAllPlaces() calls getAllPlaces() on placesApiClient`() = runBlocking {
        coEvery { placesApiClient.getAllPlaces() } returns mutableListOf()
        placesRepository.getAllPlaces()
        coVerify(exactly = 1) { placesApiClient.getAllPlaces() }
    }

    @After
    fun teardown() = clearMocks(placesApiClient)
}
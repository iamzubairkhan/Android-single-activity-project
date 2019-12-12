package com.close.svea.refactoringsample.ui

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.util.callOnCleared
import com.close.svea.refactoringsample.utils.NetworkUtils
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import io.mockk.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainViewModelTest : KoinTest {

    private val application = ApplicationProvider.getApplicationContext<Application>()
    private val placesRepository = mockk<PlacesRepository>()
    private val networkUtils = mockk<NetworkUtils>()
    private val mainViewModel = MainViewModel(application, placesRepository, networkUtils)

    @Test
    fun `connected to internet, on getAllPlaces() call, first show then hide progressbar`() {
        every { networkUtils.isConnectedToNetwork() } returns true
        coEvery { placesRepository.getAllPlaces() } returns mutableListOf()
        val testObserver = mainViewModel.isLoadingLiveData.test()

        mainViewModel.getAllPlaces()

        testObserver
            .assertValue(false)
            .assertHistorySize(2)
            .assertValueHistory(true, false)
    }

    @Test
    fun `not connected to internet, on getAllPlaces() call, does not show progressbar`() {
        every { networkUtils.isConnectedToNetwork() } returns false
        val testObserver = mainViewModel.isLoadingLiveData.test()

        mainViewModel.getAllPlaces()

        testObserver.assertNoValue()
    }

    @Test
    fun `connected to internet, call to getAllPlaces calls repository's getAllPlaces`() {
        every { networkUtils.isConnectedToNetwork() } returns true
        coEvery { placesRepository.getAllPlaces() } returns mutableListOf()

        mainViewModel.getAllPlaces()

        coVerify(exactly = 1) { placesRepository.getAllPlaces() }
    }

    @Test
    fun `not connected to internet, call to getAllPlaces does not call repository's getAllPlaces`() {
        every { networkUtils.isConnectedToNetwork() } returns false

        mainViewModel.getAllPlaces()

        coVerify { placesRepository wasNot Called }
    }

    @Test
    fun `non empty response from repository updates placesLiveData`() {
        every { networkUtils.isConnectedToNetwork() } returns true
        val responseList = mutableListOf<Place>(mockk(), mockk(), mockk())
        coEvery { placesRepository.getAllPlaces() } returns responseList

        mainViewModel.getAllPlaces()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(responseList)
        assertThat(mainViewModel.placesLiveData.value).hasSize(3)
    }

    @Test
    fun `empty response from repository does not update placesLiveData`() {
        every { networkUtils.isConnectedToNetwork() } returns true
        coEvery { placesRepository.getAllPlaces() } returns mutableListOf()
        val testObserver = mainViewModel.placesLiveData.test()

        mainViewModel.getAllPlaces()

        testObserver.assertNoValue()
    }

    @Test
    fun `mainViewModel destroyed, clears placesLiveData`() {
        every { networkUtils.isConnectedToNetwork() } returns true
        val responseList = mutableListOf<Place>(mockk(), mockk(), mockk())
        coEvery { placesRepository.getAllPlaces() } returns responseList

        mainViewModel.getAllPlaces()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(responseList)

        mainViewModel.callOnCleared()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(emptyList<Place>())
    }

    @After
    fun tearDown() {
        clearMocks(placesRepository, networkUtils)
        stopKoin()
    }
}

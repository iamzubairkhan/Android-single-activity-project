package com.close.svea.refactoringsample.ui

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.data.PlacesRepository
import com.close.svea.refactoringsample.data.model.Place
import com.close.svea.refactoringsample.util.callOnCleared
import com.close.svea.refactoringsample.utils.NetworkUtils
import com.close.svea.refactoringsample.utils.NetworkUtils.isConnectedToNetwork
import com.google.common.truth.Truth.assertThat
import com.jraska.livedata.test
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private val application = ApplicationProvider.getApplicationContext<Application>()
    private val placesRepository = mockk<PlacesRepository>()
    private val mainViewModel = MainViewModel(application, placesRepository)

    @Before
    fun setUp() = mockkObject(NetworkUtils)

    @Test
    fun `connected to internet, on getAllPlaces() call, first show then hide progressbar`() {
        every { isConnectedToNetwork(application) } returns true
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
        every { isConnectedToNetwork(application) } returns false
        val testObserver = mainViewModel.isLoadingLiveData.test()

        mainViewModel.getAllPlaces()

        testObserver.assertNoValue()
    }

    @Test
    fun `connected to internet, call to getAllPlaces calls repository's getAllPlaces`() {
        every { isConnectedToNetwork(application) } returns true
        coEvery { placesRepository.getAllPlaces() } returns mutableListOf()

        mainViewModel.getAllPlaces()

        coVerify(exactly = 1) { placesRepository.getAllPlaces() }
    }

    @Test
    fun `not connected to internet, call to getAllPlaces does not call repository's getAllPlaces`() {
        every { isConnectedToNetwork(application) } returns false

        mainViewModel.getAllPlaces()

        coVerify { placesRepository wasNot Called }
    }

    @Test
    fun `non empty response from repository updates placesLiveData`() {
        every { isConnectedToNetwork(application) } returns true
        val responseList = mutableListOf<Place>(mockk(), mockk(), mockk())
        coEvery { placesRepository.getAllPlaces() } returns responseList

        mainViewModel.getAllPlaces()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(responseList)
        assertThat(mainViewModel.placesLiveData.value).hasSize(3)
    }

    @Test
    fun `empty response from repository does not update placesLiveData`() {
        every { isConnectedToNetwork(application) } returns true
        coEvery { placesRepository.getAllPlaces() } returns mutableListOf()
        val testObserver = mainViewModel.placesLiveData.test()

        mainViewModel.getAllPlaces()

        testObserver.assertNoValue()
    }

    @Test
    fun `mainViewModel destroyed, clears placesLiveData`() {
        every { isConnectedToNetwork(application) } returns true
        val responseList = mutableListOf<Place>(mockk(), mockk(), mockk())
        coEvery { placesRepository.getAllPlaces() } returns responseList

        mainViewModel.getAllPlaces()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(responseList)

        mainViewModel.callOnCleared()

        assertThat(mainViewModel.placesLiveData.value).isEqualTo(emptyList<Place>())
    }

    @After
    fun tearDown() = clearMocks(placesRepository, NetworkUtils)
}

package com.example.weatherapp.presentation.main_screen

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.location.FakeLocationTrackerError
import com.example.weatherapp.data.location.FakeLocationTrackerNull
import com.example.weatherapp.data.location.FakeLocationTrackerSuccess
import com.example.weatherapp.data.remote.api.FakeHttpErrorApi
import com.example.weatherapp.data.remote.api.FakeIOErrorApi
import com.example.weatherapp.data.remote.api.FakeSuccessApi
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.data.repository.FakeLocationRepository
import com.example.weatherapp.data.repository.FakeWeatherRepository
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetUserLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import com.example.weatherapp.utils.compareResourceLists
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MainViewModelTest {

    private lateinit var locationMock: Location
    private val receivedUiStates = mutableListOf<Resource<Weather>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @Before
    fun setUp() {
        locationMock = mock(Location::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return correct data when location and network request is successful`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerSuccess())),
            SavedStateHandle()
        )

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()

        assert(compareResourceLists(
            listOf(
                Resource.Loading(),
                Resource.Success(mockWeather.toWeather())
        ),
            receivedUiStates)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Http Error when network request fails`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeHttpErrorApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerSuccess())),
            SavedStateHandle()
        )
        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()

        assert(compareResourceLists(
            listOf(
                Resource.Loading(),
                Resource.Error("HTTP 500 Response.error()")
            ),
            receivedUiStates)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return IO Error when network request fails`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeIOErrorApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerSuccess())),
            SavedStateHandle()
        )

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()

        assert(compareResourceLists(
            listOf(
                Resource.Loading(),
                Resource.Error("Couldn't reach server. Check your internet connection.")
            ),
            receivedUiStates)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Error when location request fails`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerError())),
            SavedStateHandle()
        )
        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()

        assert(compareResourceLists(
            listOf(
                Resource.Loading(),
                Resource.Error("Error fetching location")
            ),
            receivedUiStates)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return location not found error when location is null`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerNull())),
            SavedStateHandle()
        )
        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()

        assert(compareResourceLists(
            listOf(
                Resource.Loading(),
                Resource.Error("Location not found")
            ),
            receivedUiStates)
        )
    }
}
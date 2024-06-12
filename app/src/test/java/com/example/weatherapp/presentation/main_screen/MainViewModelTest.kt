package com.example.weatherapp.presentation.main_screen

import androidx.lifecycle.SavedStateHandle
import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.location.FakeLocationTracker
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
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private val receivedUiStates = mutableListOf<Resource<Weather>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return data when network request is successful`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(
            GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())),
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTracker())),
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
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTracker())),
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
            GetUserLocationUseCase(FakeLocationRepository(FakeLocationTracker())),
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
}
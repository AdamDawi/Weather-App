package com.example.weatherapp.presentation.main_screen

import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.api.FakeHttpErrorApi
import com.example.weatherapp.data.remote.api.FakeIOErrorApi
import com.example.weatherapp.data.remote.api.FakeSuccessApi
import com.example.weatherapp.data.repository.FakeWeatherRepository
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private val receivedUiStates = mutableListOf<Resource>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return data when network request is successful`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())))

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()
        assertEquals(
            listOf(
                Resource.Loading,
                Resource.Success(mockWeather)
            ),
            receivedUiStates
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Http Error when network request fails`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeHttpErrorApi())))
        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()
        assertEquals(
            listOf(
                Resource.Loading,
                Resource.Error("HTTP 500 Response.error()")
            ),
            receivedUiStates
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return IO Error when network request fails`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeIOErrorApi())))

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(2)
            .launchIn(this)

        advanceUntilIdle()
        assertEquals(
            listOf(
                Resource.Loading,
                Resource.Error("Couldn't reach server. Check your internet connection.")
            ),
            receivedUiStates
        )
    }
}
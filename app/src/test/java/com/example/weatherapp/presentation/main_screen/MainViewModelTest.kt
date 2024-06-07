package com.example.weatherapp.presentation.main_screen

import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.api.FakeHttpErrorApi
import com.example.weatherapp.data.remote.api.FakeIOErrorApi
import com.example.weatherapp.data.remote.api.FakeSuccessApi
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.data.repository.FakeWeatherRepository
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return data when network request is successful`() = runTest {
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi())))

        viewModel.getWeather(mockWeather.latitude, mockWeather.longitude)
        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(mockWeather.toWeather(), viewModel.state.value.weather)
        assertEquals(null, viewModel.state.value.error)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Http Error when network request fails`() = runTest {
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeHttpErrorApi())))

        viewModel.getWeather(mockWeather.latitude, mockWeather.longitude)
        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.weather)
        assertEquals("HTTP 500 Response.error()", viewModel.state.value.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return IO Error when network request fails`() = runTest {
        val viewModel = MainViewModel(GetWeatherUseCase(FakeWeatherRepository(FakeIOErrorApi())))

        viewModel.getWeather(mockWeather.latitude, mockWeather.longitude)
        advanceUntilIdle()
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(null, viewModel.state.value.weather)
        assertEquals("Couldn't reach server. Check your internet connection.", viewModel.state.value.error)
    }
}
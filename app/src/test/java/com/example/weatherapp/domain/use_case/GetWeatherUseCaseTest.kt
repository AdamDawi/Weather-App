package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.api.FakeHttpErrorApi
import com.example.weatherapp.data.remote.api.FakeIOErrorApi
import com.example.weatherapp.data.remote.api.FakeSuccessApi
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.data.repository.FakeWeatherRepository
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import com.example.weatherapp.utils.compareResourceLists
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GetWeatherUseCaseTest {

    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private val receivedUiStates = mutableListOf<Resource<Weather>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Success when network request is successful`() = runTest {

        Assert.assertTrue(receivedUiStates.isEmpty())
        getWeatherUseCase = GetWeatherUseCase(FakeWeatherRepository(FakeSuccessApi()))

        getWeatherUseCase(longitude = mockWeather.longitude, latitude = mockWeather.latitude).onEach {
            receivedUiStates.add(it)
        }.launchIn(this)

        advanceUntilIdle()

        assert(
            compareResourceLists(
                listOf(
                    Resource.Success(mockWeather.toWeather())
                ),
            receivedUiStates)
        )
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Http Error when network request fails`() = runTest {

        Assert.assertTrue(receivedUiStates.isEmpty())
        getWeatherUseCase = GetWeatherUseCase(FakeWeatherRepository(FakeHttpErrorApi()))

        getWeatherUseCase(longitude = mockWeather.longitude, latitude = mockWeather.latitude).onEach {
            receivedUiStates.add(it)
        }.launchIn(this)

        advanceUntilIdle()

        assert(
            compareResourceLists(
                listOf<Resource<Weather>>(
                    Resource.Error("HTTP 500 Response.error()")
                ),
                receivedUiStates)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return IO Error when network request fails`() = runTest {

        Assert.assertTrue(receivedUiStates.isEmpty())
        getWeatherUseCase = GetWeatherUseCase(FakeWeatherRepository(FakeIOErrorApi()))

        getWeatherUseCase(longitude = mockWeather.longitude, latitude = mockWeather.latitude).onEach {
            receivedUiStates.add(it)
        }.launchIn(this)

        advanceUntilIdle()

        assert(
            compareResourceLists(
                listOf<Resource<Weather>>(
                    Resource.Error("Couldn't reach server. Check your internet connection.")
                ),
                receivedUiStates)
        )
    }
}
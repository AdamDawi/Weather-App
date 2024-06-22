package com.example.weatherapp.presentation.main_screen

import android.location.Location
import androidx.lifecycle.SavedStateHandle
import com.example.weatherapp.common.Resource
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.use_case.GetUserLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherUseCase
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import com.example.weatherapp.utils.assertResourceListsEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.anyDouble
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class MainViewModelTest {
    private lateinit var locationMock: Location
    private val receivedUiStates = mutableListOf<Resource<Weather>>()
    private lateinit var getUserLocationUseCaseMock: GetUserLocationUseCase
    private lateinit var getWeatherUseCaseMock: GetWeatherUseCase
    private lateinit var viewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    @Before
    fun setUp() {
        locationMock = mock(Location::class.java)
        getWeatherUseCaseMock = mock(GetWeatherUseCase::class.java)
        getUserLocationUseCaseMock = mock(GetUserLocationUseCase::class.java)
        viewModel =
            MainViewModel(getWeatherUseCaseMock, getUserLocationUseCaseMock, SavedStateHandle())
        successLocationFlow()
        successWeatherFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should pass correct parameters to weather use case`() = runTest {
        val ac = ArgumentCaptor.forClass(Double::class.java)
        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.first()

        advanceUntilIdle()

        verify(getWeatherUseCaseMock, times(2)).invoke(ac.capture(), ac.capture())
        val params = ac.allValues
        assertEquals(21.0122287, params[0], 0.00000001)
        assertEquals(52.2296756, params[1], 0.00000001)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return correct data when location and network request is successful`() = runTest {
        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(1)
            .launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Success(mockWeather.toWeather())
            ),
            receivedUiStates
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Http Error when network request fails`() = runTest {
        apiHttpError()

        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(1)
            .launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Error("HTTP 500 Response.error()")
            ),
            receivedUiStates
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return IO Error when network request fails`() = runTest {
        apiIOError()

        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(1)
            .launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Error("Couldn't reach server. Check your internet connection.")
            ),
            receivedUiStates
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Error when location request fails`() = runTest {
        locationError()

        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(1)
            .launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Error("Error fetching location")
            ),
            receivedUiStates
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return location not found error when location is null`() = runTest {
        locationNull()

        assertTrue(receivedUiStates.isEmpty())

        viewModel.state.onEach {
            receivedUiStates.add(it)
        }.take(1)
            .launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Error("Location not found")
            ),
            receivedUiStates
        )
    }

    private fun successLocationFlow() {
        `when`(locationMock.longitude).thenReturn(21.0122287)
        `when`(locationMock.latitude).thenReturn(52.2296756)
        `when`(getUserLocationUseCaseMock.invoke()).thenReturn(flowOf(Resource.Success(locationMock)))
    }

    private fun successWeatherFlow() {
        `when`(
            getWeatherUseCaseMock.invoke(
                anyDouble(),
                anyDouble()
            )
        ).thenReturn(flowOf(Resource.Success(mockWeather.toWeather())))
    }

    private fun apiHttpError() {
        `when`(
            getWeatherUseCaseMock.invoke(
                anyDouble(),
                anyDouble()
            )
        ).thenReturn(flowOf(Resource.Error("HTTP 500 Response.error()")))
    }

    private fun apiIOError() {
        `when`(getWeatherUseCaseMock.invoke(anyDouble(), anyDouble())).thenReturn(
            flowOf(
                Resource.Error("Couldn't reach server. Check your internet connection.")
            )
        )
    }

    private fun locationError() {
        `when`(getUserLocationUseCaseMock.invoke()).thenReturn(
            flowOf(
                Resource.Error("Error fetching location")
            )
        )
    }

    private fun locationNull() {
        `when`(getUserLocationUseCaseMock.invoke()).thenReturn(flowOf(Resource.Success(null)))
    }
}
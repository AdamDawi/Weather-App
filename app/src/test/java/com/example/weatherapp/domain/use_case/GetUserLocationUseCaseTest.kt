package com.example.weatherapp.domain.use_case

import android.location.Location
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.location.FakeLocationTrackerError
import com.example.weatherapp.data.location.FakeLocationTrackerSuccess
import com.example.weatherapp.data.repository.FakeLocationRepository
import com.example.weatherapp.utils.ReplaceMainDispatcherRule
import com.example.weatherapp.utils.assertResourceListsEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class GetUserLocationUseCaseTest {

    // region constants

    // endregion constants

    // region helper fields
    private lateinit var getUserLocationUseCase: GetUserLocationUseCase
    private val receivedUiStates = mutableListOf<Resource<Location>>()
    // endregion helper fields

    @OptIn(ExperimentalCoroutinesApi::class)
    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    // region helper methods
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Success when fetch request is successful`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        getUserLocationUseCase = GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerSuccess()))

        getUserLocationUseCase().onEach {
            receivedUiStates.add(it)
        }.launchIn(this)

        advanceUntilIdle()
        assertEquals(receivedUiStates[0].data?.latitude, 52.2296756)
        assertEquals(receivedUiStates[0].data?.longitude, 21.0122287)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return Error when fetch request is failed`() = runTest {
        assertTrue(receivedUiStates.isEmpty())
        getUserLocationUseCase = GetUserLocationUseCase(FakeLocationRepository(FakeLocationTrackerError()))

        getUserLocationUseCase().onEach {
            receivedUiStates.add(it)
        }.launchIn(this)

        advanceUntilIdle()

        assertResourceListsEquals(
            listOf(
                Resource.Error("Error fetching location")
            ),
            receivedUiStates
        )

    }
    // endregion helper methods

    // region helper classes

    // endregion helper classes
}
package com.example.weatherapp.data.location

import android.location.Location
import kotlinx.coroutines.delay
import org.mockito.Mockito

class FakeLocationTrackerError: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        delay(1000)
        val mockLocation = Mockito.mock(Location::class.java)

        Mockito.`when`(mockLocation.latitude).thenReturn(0.0)
        Mockito.`when`(mockLocation.longitude).thenReturn(0.0)
        throw Exception("Error fetching location")
    }
}
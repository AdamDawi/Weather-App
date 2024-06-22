package com.example.weatherapp.data.location

import android.location.Location
import kotlinx.coroutines.delay
import org.mockito.Mockito

class FakeLocationTrackerSuccess: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        delay(1000)
        val mockLocation = Mockito.mock(Location::class.java)

        Mockito.`when`(mockLocation.latitude).thenReturn(52.2296756)
        Mockito.`when`(mockLocation.longitude).thenReturn(21.0122287)
        return mockLocation
    }
}
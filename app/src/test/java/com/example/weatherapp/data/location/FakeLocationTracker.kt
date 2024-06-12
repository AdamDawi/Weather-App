package com.example.weatherapp.data.location

import android.location.Location
import org.mockito.Mockito

class FakeLocationTracker: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        val mockLocation = Mockito.mock(Location::class.java)

        Mockito.`when`(mockLocation.latitude).thenReturn(52.2296756)
        Mockito.`when`(mockLocation.longitude).thenReturn(21.0122287)
        return mockLocation
    }
}
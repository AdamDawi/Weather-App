package com.example.weatherapp.data.location

import android.location.Location
import org.mockito.Mockito

class FakeLocationTrackerError: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        val mockLocation = Mockito.mock(Location::class.java)

        Mockito.`when`(mockLocation.latitude).thenReturn(0.0)
        Mockito.`when`(mockLocation.longitude).thenReturn(0.0)
        return throw Exception("error fetching location")
    }
}
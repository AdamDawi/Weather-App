package com.example.weatherapp.data.remote.api

import android.location.Location
import com.example.weatherapp.data.location.LocationTracker
import org.mockito.Mockito

class FakeLocationTrackerSuccess: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        val mockLocation = Mockito.mock(Location::class.java)

        Mockito.`when`(mockLocation.latitude).thenReturn(52.2296756)
        Mockito.`when`(mockLocation.longitude).thenReturn(21.0122287)
        return mockLocation
    }
}
package com.example.weatherapp.data.location

import android.location.Location
import com.example.weatherapp.common.mockLocation

class FakeLocationTracker: LocationTracker {
    override suspend fun getCurrentLocation(): Location {
        return mockLocation.apply {
            latitude = 52.0
            longitude = 23.372
        }
    }
}
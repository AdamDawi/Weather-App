package com.example.weatherapp.data.location

import android.location.Location
import kotlinx.coroutines.delay

class FakeLocationTrackerNull: LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        delay(1000)
        return null
    }
}
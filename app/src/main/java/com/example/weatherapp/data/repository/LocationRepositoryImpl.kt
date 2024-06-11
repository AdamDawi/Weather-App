package com.example.weatherapp.data.repository

import android.location.Location
import com.example.weatherapp.data.location.LocationTracker
import com.example.weatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationTracker: LocationTracker
) : LocationRepository {
    override suspend fun getCurrentLocation(): Location? {
        return locationTracker.getCurrentLocation()
    }
}
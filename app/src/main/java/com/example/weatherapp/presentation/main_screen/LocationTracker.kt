package com.example.weatherapp.presentation.main_screen

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}
package com.example.weatherapp.common

import android.location.Location

sealed class ResourceLocation{
    object Loading : ResourceLocation()
    data class Success(val data: Location?) : ResourceLocation()
    data class Error(val message: String) : ResourceLocation()
}
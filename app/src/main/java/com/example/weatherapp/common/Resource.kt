package com.example.weatherapp.common

import com.example.weatherapp.data.remote.dto.WeatherDto

sealed class Resource{
    data object Loading : Resource()
    data class Success(val data: WeatherDto) : Resource()
    data class Error(val message: String) : Resource()
}
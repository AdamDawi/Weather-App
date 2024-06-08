package com.example.weatherapp.common

import com.example.weatherapp.domain.model.Weather

sealed class Resource{
    object Loading : Resource()
    data class Success(val data: Weather) : Resource()
    data class Error(val message: String) : Resource()
}
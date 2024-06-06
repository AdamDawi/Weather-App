package com.example.weatherapp.domain.use_case

import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase
@Inject constructor(
    private val repository: WeatherRepository
){
    suspend operator fun invoke(longitude: Double, latitude: Double): Weather {
        return repository.getWeather(longitude = longitude.toString(), latitude = latitude.toString()).toWeather()
    }
}

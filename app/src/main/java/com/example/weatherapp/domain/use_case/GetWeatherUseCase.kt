package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

open class GetWeatherUseCase
@Inject constructor(
    private val repository: WeatherRepository
){
    open operator fun invoke(longitude: Double, latitude: Double): Flow<Resource<Weather>> = flow {
            Timber.d("Fetching current weather")
            try {
                val result = repository.getWeather(longitude = longitude.toString(), latitude = latitude.toString())
                emit(Resource.Success(result.toWeather()))
            }catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }catch (e: Exception){
                Timber.e(e.stackTraceToString())
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
            }
    }.onCompletion {
        Timber.d("Weather fetch completed")
    }
}

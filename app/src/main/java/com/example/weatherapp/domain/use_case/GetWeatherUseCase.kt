package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetWeatherUseCase
@Inject constructor(
    private val repository: WeatherRepository
){
    operator fun invoke(longitude: Double, latitude: Double): Flow<Resource> = flow {
        while(true){
            Timber.tag("Flow").d("Fetching current stock prices")
            try {
                val result = repository.getWeather(longitude = longitude.toString(), latitude = latitude.toString())
                emit(Resource.Success(result))
            }catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }catch (e: Exception){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
            }
            delay(5000)
        }
    }
}

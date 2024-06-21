package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.WeatherDto
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeHttpErrorApi: WeatherApi {
    override suspend fun getWeather(
        latitude: String,
        longitude: String,
        current: String,
        daily: String,
        hourly: String,
        pastDays: Int,
        timezone: String
    ): WeatherDto {
        delay(1000)
        throw HttpException(
            Response.error<WeatherDto>(
                500,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )
        )
    }
}
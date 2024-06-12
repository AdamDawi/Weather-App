package com.example.weatherapp.common

import android.location.Location
import com.example.weatherapp.data.remote.dto.Current
import com.example.weatherapp.data.remote.dto.CurrentUnits
import com.example.weatherapp.data.remote.dto.WeatherDto

val mockCurrent = Current(
    interval = 900,
    time = "2024-06-08T19:15",
    temperature_2m = 19.1,
    is_day = 1,
    rain = 0.0,
    wind_speed_10m = 2.3,
    cloud_cover = 77.0,
    weather_code = 3,
    relative_humidity_2m = 61.0
)
val mockCurrentUnits = CurrentUnits(
    temperature_2m = "°C",
    rain = "mm",
    wind_speed_10m = "km/h",
    time = "iso8601",
    interval = "seconds",
    is_day = "",
    cloud_cover = "%",
    weather_code = "wmo code",
    relative_humidity_2m = "%"
)
val mockWeather = WeatherDto(
    latitude = 52.0,
    longitude = 23.372,
    generationtime_ms = 0.0380277633666992,
    utc_offset_seconds = 7200,
    timezone = "Europe/Warsaw",
    timezone_abbreviation = "CEST",
    elevation = 146,
    current = mockCurrent,
    current_units = mockCurrentUnits
)

const val mockCurrentDate: String = "17:50:33 09/06/2024"
val mockLocation = Location("mockProvider")


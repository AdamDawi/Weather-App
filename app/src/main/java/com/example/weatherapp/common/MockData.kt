package com.example.weatherapp.common

import com.example.weatherapp.data.remote.dto.Current
import com.example.weatherapp.data.remote.dto.CurrentUnits
import com.example.weatherapp.data.remote.dto.Daily
import com.example.weatherapp.data.remote.dto.DailyUnits
import com.example.weatherapp.data.remote.dto.Hourly
import com.example.weatherapp.data.remote.dto.HourlyUnits
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

val mockDaily = Daily(
    time = listOf(
        "2024-06-13",
        "2024-06-14",
        "2024-06-15",
        "2024-06-16",
        "2024-06-17",
        "2024-06-18",
        "2024-06-19"
    ),
    weather_code = listOf(61, 61, 3, 3, 3, 3, 3),
    temperature_2m_max = listOf(17.6, 20.0, 22.7, 24.7, 25.8, 28.3, 31.3),
    temperature_2m_min = listOf(10.8, 9.1, 10.7, 12.2, 15.7, 14.1, 16.2),
    sunrise = listOf(
        "2024-06-13T02:06",
        "2024-06-14T02:06",
        "2024-06-15T02:05",
        "2024-06-16T02:05",
        "2024-06-17T02:05",
        "2024-06-18T02:05",
        "2024-06-19T02:06"),
    sunset = listOf(
        "2024-06-13T18:47",
        "2024-06-14T18:47",
        "2024-06-15T18:48",
        "2024-06-16T18:48",
        "2024-06-17T18:49",
        "2024-06-18T18:49",
        "2024-06-19T18:49"
    )
)

val mockDailyUnits = DailyUnits(
    time = "iso8601",
    weather_code = "wmo code",
    temperature_2m_max = "°C",
    temperature_2m_min = "°C",
    sunrise = "iso8601",
    sunset = "iso8601"
)

val mockHourly = Hourly(
    time = listOf(
        "2024-06-13T00:00",
        "2024-06-13T01:00",
        "2024-06-13T02:00",
        "2024-06-13T03:00",
        "2024-06-13T04:00",
        "2024-06-13T05:00",
        "2024-06-13T06:00",
        "2024-06-13T07:00",
        "2024-06-13T08:00",
        "2024-06-13T09:00",
        "2024-06-13T10:00",
        "2024-06-13T11:00",
        "2024-06-13T12:00",
        "2024-06-13T13:00",
        "2024-06-13T14:00",
        "2024-06-13T15:00",
        "2024-06-13T16:00",
        "2024-06-13T17:00",
        "2024-06-13T18:00",
        "2024-06-13T19:00",
        "2024-06-13T20:00",
        "2024-06-13T21:00",
        "2024-06-13T22:00",
        "2024-06-13T23:00",
    ),
    temperature_2m = listOf(12.3, 11.9, 11.5, 10.8, 11.8, 13.3, 15.3, 16.0, 17.3, 17.6, 17.6, 16.5, 15.2, 14.3, 13.7, 13.4, 13.2, 12.8, 12.3, 11.9, 11.6, 11.5, 11.2, 10.9),
    relative_humidity_2m = listOf(75, 76, 77, 84, 83, 78, 65, 64, 57, 54, 52, 63, 72, 77, 80, 83, 88, 89, 89, 90, 92, 92, 92, 91),
    weather_code = listOf(2, 3, 3, 2, 2, 3, 2, 3, 3, 3, 3, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61, 61),
    wind_speed_10m = listOf(5.1, 4.6, 4.7, 4.5, 4.2, 5.2, 6.2, 5.4, 4.2, 1.9, 1.0, 6.1, 8.7, 8.7, 8.4, 6.0, 7.6, 8.6, 7.2, 5.9, 7.1, 8.6, 8.7, 8.6)
)

val mockHourlyUnits = HourlyUnits(
    time = "iso8601",
    temperature_2m = "°C",
    relative_humidity_2m = "%",
    weather_code = "wmo code",
    wind_speed_10m = "km/h"
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
    current_units = mockCurrentUnits,
    daily = mockDaily,
    daily_units = mockDailyUnits,
    hourly = mockHourly,
    hourly_units = mockHourlyUnits
)

const val mockCurrentDate: String = "17:50:33"


package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.common.mockCurrentLocalDateTime
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.common.provideFormattedTimeToDayOfWeekDate
import com.example.weatherapp.common.provideFormattedTimeToHourMinuteSecond
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.ui.theme.DarkerWhite
import com.example.weatherapp.presentation.ui.theme.LightOrange
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    currentLocalDateTime: String,
    weatherData: Weather,
    location: String,
    onThemeUpdate: () -> Unit,
    isDarkTheme: Boolean
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 2)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Scaffold(
        topBar = {
            WeatherHeader(
                provideFormattedTimeToHourMinuteSecond(currentLocalDateTime),
                isDarkTheme,
                onThemeUpdate
            )
        }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WeatherLocation(location)
                DailyWeatherForecast(weatherData, listState, snapBehavior)
                WeatherCurrentTemperatureChart(weatherData, LocalDateTime.parse(currentLocalDateTime))
                WeatherDetailCards(weatherData)
                SunPathCard(weatherData)
            }
        }

    }
}

@Composable
private fun WeatherHeader(currentDate: String, darkTheme: Boolean, onThemeUpdate: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThemeSwitcher(darkTheme = darkTheme) {
            onThemeUpdate()
        }
        Text(
            text = "Last update time: $currentDate",
            fontSize = 12.sp,
            modifier = Modifier.padding(8.dp),
            color = if (darkTheme) DarkerWhite else Color.Gray
        )
    }
}

@Composable
private fun WeatherLocation(location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = location,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun DailyWeatherForecast(
    weatherData: Weather,
    listState: LazyListState,
    snapBehavior: FlingBehavior
) {
    LazyRow(
        state = listState,
        flingBehavior = snapBehavior,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 70.dp),
        horizontalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        itemsIndexed(weatherData.daily.time) { index, _ ->
            DailyWeatherCard(
                weatherCode = weatherData.daily.weather_code[index],
                maxTemperature = weatherData.daily.temperature_2m_max[index],
                isDay = if (index == 2) weatherData.current.is_day == 1 else true,
                formattedTime = provideFormattedTimeToDayOfWeekDate(weatherData.daily.time[index]),
                temperatureUnit = weatherData.daily_units.temperature_2m_max,
                minTemperature = weatherData.daily.temperature_2m_min[index]
            )
        }
    }
}

@Composable
private fun WeatherCurrentTemperatureChart(
    weatherData: Weather,
    currentLocalDateTime: LocalDateTime
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 16.dp)
    ) {
        Text(
            text = "Current weather details",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
    CurrentTemperatureChartCard(
        currentTemperature = weatherData.current.temperature_2m,
        currentTemperatureUnit = weatherData.current_units.temperature_2m,
        maxTemperatureToday = weatherData.daily.temperature_2m_max[2],
        fullDayTemperatureData = weatherData.hourly.temperature_2m,
        fullDayTimeData = weatherData.hourly.time,
        currentLocalDateTime = currentLocalDateTime
    )
}

@Composable
private fun WeatherDetailCards(weatherData: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailsCard(
            title = "Rain",
            value = weatherData.current.rain.toString(),
            unit = weatherData.current_units.rain,
            icon = painterResource(id = R.drawable.ic_rain)
        )
        WeatherDetailsCard(
            title = "Wind speed",
            value = weatherData.current.wind_speed_10m.toString(),
            unit = weatherData.current_units.wind_speed_10m,
            icon = painterResource(id = R.drawable.ic_wind)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailsCard(
            title = "Humidity",
            value = weatherData.current.relative_humidity_2m.toString(),
            unit = weatherData.current_units.relative_humidity_2m,
            icon = painterResource(
                id = if (weatherData.current.relative_humidity_2m < 20.0) R.drawable.ic_humidity_low
                else if (weatherData.current.relative_humidity_2m < 80.0) R.drawable.ic_humidity_medium
                else R.drawable.ic_humidity_high
            )
        )
        WeatherDetailsCard(
            title = "Cloud cover",
            value = weatherData.current.cloud_cover.toString(),
            unit = weatherData.current_units.cloud_cover,
            icon = painterResource(
                id = if (weatherData.current.cloud_cover < 60.0) R.drawable.ic_cloud
                else R.drawable.ic_cloud_filled
            )
        )
    }
}

@Composable
private fun SunPathCard(weatherData: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SunsetSunriseCard(
            sunriseTime = provideFormattedSunDate(weatherData.daily.sunrise[2]),
            sunsetTime = provideFormattedSunDate(weatherData.daily.sunset[2]),
            sunPathProgressPercent = calculateSunPathProgressPercent(
                LocalDateTime.parse(weatherData.daily.sunrise[2]),
                LocalDateTime.parse(weatherData.daily.sunset[2]),
                LocalDateTime.now(),
            ),
            sunRadius = 8.dp,
            gradientStartColor = LightOrange
        )
    }
}

private fun provideFormattedSunDate(time: String): String {
    val localDateTime = LocalDateTime.parse(time)
    return "${localDateTime.hour}:${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}"
}

private fun calculateSunPathProgressPercent(sunriseTime: LocalDateTime, sunsetTime: LocalDateTime, currentTime: LocalDateTime): Float {
    val totalDayMinutes = sunriseTime.until(sunsetTime, ChronoUnit.MINUTES)
    val elapsedMinutes = sunriseTime.until(currentTime, ChronoUnit.MINUTES)
    return ((elapsedMinutes.toFloat() / totalDayMinutes.toFloat()) * 100).coerceAtMost(100f)
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentDefaultPreview() {
    WeatherAppTheme {
        Surface {
            WeatherContent(
                currentLocalDateTime = mockCurrentLocalDateTime,
                weatherData = mockWeather.toWeather().copy(
                    current = mockWeather.toWeather().current.copy(
                        relative_humidity_2m = 15.0, cloud_cover = 20.0
                    )
                ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentThunderstormPreview() {
    WeatherAppTheme {
        Surface {
            WeatherContent(
                currentLocalDateTime = mockCurrentLocalDateTime,
                weatherData = mockWeather.toWeather().copy(
                    current = mockWeather.toWeather().current.copy(
                        weather_code = 95,
                        relative_humidity_2m = 25.0,
                        cloud_cover = 80.0
                    )
                ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun WeatherContentRainAndSnowPreview() {
    WeatherAppTheme {
        Surface {
            WeatherContent(
                currentLocalDateTime = mockCurrentLocalDateTime,
                weatherData = mockWeather.toWeather().copy(
                    current = mockWeather.toWeather().current.copy(
                        weather_code = 66, relative_humidity_2m = 80.0
                    )
                ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentFrostPreview() {
    WeatherAppTheme {
        Surface {
            WeatherContent(
                currentLocalDateTime = mockCurrentLocalDateTime,
                weatherData = mockWeather.toWeather().copy(
                    current = mockWeather.toWeather().current.copy(
                        weather_code = 51
                    )
                ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}

package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
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
import com.example.weatherapp.common.mockCurrentDate
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.ui.theme.DarkerWhite
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    currentDate: String,
    weatherData: Weather,
    location: String,
    onThemeUpdate: () -> Unit,
    darkTheme: Boolean
) {

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 2)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stickyHeader {
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
                    modifier = Modifier
                        .padding(8.dp),
                    color = if (darkTheme) DarkerWhite else Color.Gray
                )
            }
        }
        item {
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
            LazyRow(
                state = listState,
                flingBehavior = snapBehavior,
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 70.dp),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                itemsIndexed(
                    weatherData.daily.time
                ){index, _ ->
                    DailyWeatherCard(
                        weatherData = weatherData,
                        index = index
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Today's weather details",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CurrentWeatherDetailsCard(
                    title = "Rain",
                    value = weatherData.current.rain.toString(),
                    unit = weatherData.current_units.rain,
                    icon = painterResource(id = R.drawable.ic_rain)
                )
                CurrentWeatherDetailsCard(
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
                CurrentWeatherDetailsCard(
                    title = "Humidity",
                    value = weatherData.current.relative_humidity_2m.toString(),
                    unit = weatherData.current_units.relative_humidity_2m,
                    icon = painterResource(
                        id =
                        if (weatherData.current.relative_humidity_2m < 20.0) R.drawable.ic_humidity_low
                        else if (weatherData.current.relative_humidity_2m < 80.0) R.drawable.ic_humidity_medium
                        else R.drawable.ic_humidity_high
                    )
                )
                CurrentWeatherDetailsCard(
                    title = "Cloud cover",
                    value = weatherData.current.cloud_cover.toString(),
                    unit = weatherData.current_units.cloud_cover,
                    icon = painterResource(
                        id =
                        if (weatherData.current.cloud_cover < 60.0) R.drawable.ic_cloud
                        else R.drawable.ic_cloud_filled
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SunsetSunriseCard(
                    sunrise = provideFormattedSunDate(weatherData.daily.sunrise[2]),
                    sunset = provideFormattedSunDate(weatherData.daily.sunset[2]),
                    sunPathProgressPercent = calculateSunPathProgressPercent(
                        LocalDateTime.parse(weatherData.daily.sunrise[2]),
                        LocalDateTime.parse(weatherData.daily.sunset[2]),
                        LocalDateTime.now()
                        )
                )
            }
        }
    }
}

// time format is HH:mm
private fun provideFormattedSunDate(time: String): String{
    val localDateTime = LocalDateTime.parse(time)
    return "${localDateTime.hour}:${if(localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}"
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
                currentDate = mockCurrentDate,
                weatherData = mockWeather.toWeather()
                    .copy(
                        current = mockWeather.toWeather().current
                            .copy(relative_humidity_2m = 15.0, cloud_cover = 20.0)
                    ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                darkTheme = isSystemInDarkTheme()
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
                currentDate = mockCurrentDate,
                weatherData = mockWeather.toWeather()
                    .copy(
                        current = mockWeather.toWeather().current
                            .copy(
                                weather_code = 95,
                                relative_humidity_2m = 25.0,
                                cloud_cover = 80.0
                            )
                    ),
                location = "Polska, Lublin",
                onThemeUpdate = {},
                darkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentRainAndSnowPreview() {
    WeatherContent(
        currentDate = mockCurrentDate,
        weatherData = mockWeather.toWeather()
            .copy(
                current = mockWeather.toWeather().current
                    .copy(weather_code = 66, relative_humidity_2m = 80.0)
            ),
        location = "Polska, Lublin",
        onThemeUpdate = {},
        darkTheme = isSystemInDarkTheme()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentFrostPreview() {
    WeatherContent(
        currentDate = mockCurrentDate,
        weatherData = mockWeather.toWeather()
            .copy(current = mockWeather.toWeather().current.copy(weather_code = 51)),
        location = "Polska, Lublin",
        onThemeUpdate = {},
        darkTheme = isSystemInDarkTheme()
    )
}
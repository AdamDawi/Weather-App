package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.mockCurrentDate
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.ui.theme.DarkerWhite
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    currentDate: String,
    weatherData: Weather,
    location: String,
    onThemeUpdate: () -> Unit,
    darkTheme: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = location,
                fontSize = 24.sp
            )
        }
        Box(modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ){
            WeatherIconBasedOnCode(
                weatherCode = weatherData.current.weather_code,
                isDay = weatherData.current.is_day==1
            )
            Text(
                modifier = Modifier
                    .padding(top = 130.dp),
                text = "${weatherData.current.temperature_2m} ${weatherData.current_units.temperature_2m}",
                fontSize = 72.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                CardForDetail(
                    title = "Rain",
                    value = weatherData.current.rain.toString(),
                    unit = weatherData.current_units.rain
                )
            }
            item {
                CardForDetail(
                    title = "Wind speed",
                    value = weatherData.current.wind_speed_10m.toString(),
                    unit = weatherData.current_units.wind_speed_10m
                )
            }
            item {
                CardForDetail(
                    title = "Humidity",
                    value = weatherData.current.relative_humidity_2m.toString(),
                    unit = weatherData.current_units.relative_humidity_2m
                )
            }
            item {
                CardForDetail(
                    title = "Cloud cover",
                    value = weatherData.current.cloud_cover.toString(),
                    unit = weatherData.current_units.cloud_cover
                )
            }
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun WeatherContentDefaultPreview() {
    WeatherAppTheme {
        Surface {
            WeatherContent(
                currentDate = mockCurrentDate,
                weatherData = mockWeather.toWeather(),
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
                    .copy(current = mockWeather.toWeather().current.copy(weather_code = 95)),
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
            .copy(current = mockWeather.toWeather().current.copy(weather_code = 66)),
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
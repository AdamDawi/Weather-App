package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.common.provideFormattedTimeToDayOfWeekDate
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlin.math.roundToInt

@Composable
fun DailyWeatherCard(
    modifier: Modifier = Modifier,
    maxTemperature: Double,
    minTemperature: Double,
    temperatureUnit: String,
    isDay: Boolean,
    weatherCode: Int,
    formattedTime: String
) {
    Box(
        modifier = modifier
            .height(310.dp)
    ){
        // Core card with gradient
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(40.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        ),
                    )
                )
                .padding(20.dp)
                .padding(top = 28.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WeatherIconBasedOnCode(
                    weatherCode = weatherCode,
                    isDay = isDay
                )
                Text(
                    text = "${maxTemperature.roundToInt()}${temperatureUnit[0]} / " +
                            "${minTemperature.roundToInt()}${temperatureUnit[0]}",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Time label
        Box(
            modifier = Modifier
                .shadow(2.dp, CircleShape)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ){
            Text(
                modifier = Modifier.padding(8.dp),
                text = formattedTime,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DailyWeatherCardRainPreview() {
    WeatherAppTheme {
        Surface {
            DailyWeatherCard(
                weatherCode = mockWeather.daily.weather_code[1],
                maxTemperature = mockWeather.daily.temperature_2m_max[1],
                isDay = true,
                formattedTime = provideFormattedTimeToDayOfWeekDate(mockWeather.daily.time[1]),
                temperatureUnit = mockWeather.daily_units.temperature_2m_max,
                minTemperature = mockWeather.daily.temperature_2m_min[1]
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DailyWeatherCardPreview() {
    WeatherAppTheme {
        Surface {
            DailyWeatherCard(
                weatherCode = 99,
                maxTemperature = mockWeather.daily.temperature_2m_max[1],
                isDay = false,
                formattedTime = provideFormattedTimeToDayOfWeekDate(mockWeather.daily.time[2]),
                temperatureUnit = mockWeather.daily_units.temperature_2m_max,
                minTemperature = mockWeather.daily.temperature_2m_min[1]
            )
        }
    }
}
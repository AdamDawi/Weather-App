package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun WeatherIconBasedOnCode(
    modifier: Modifier = Modifier,
    weatherCode: Int,
    isDay: Boolean
) {
    Icon(
        modifier = modifier
            .size(170.dp),
        painter = painterResource(id =
        when (weatherCode) {
            0 -> if(isDay) R.drawable.clear_sky else R.drawable.clear_night
            1,2,3 -> if(isDay) R.drawable.cloudy_day else R.drawable.cloudy_night
            45, 48 -> if(isDay) R.drawable.fog_day else R.drawable.fog_night
            51, 53, 55, 56, 57 -> if(isDay) R.drawable.frost_day else R.drawable.frost_night
            61, 63, 65, 80, 81, 82 -> if(isDay) R.drawable.rainy_day else R.drawable.rainy_night
            66, 67 -> R.drawable.rain_and_snow_mix
            71, 73, 75, 77, 85, 86 -> if(isDay) R.drawable.snowy_day else R.drawable.snowy_night
            95, 96, 99-> if(isDay) R.drawable.thunderstorm_day else R.drawable.thunderstorm_night
            else -> R.drawable.clear_sky
        }),
        contentDescription = "Weather",
        tint = Color.Unspecified
    )
}

@Preview
@Composable
private fun WeatherIconBasedOnCodeRainyDayPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 65,
        isDay = true
    )
}

@Preview
@Composable
private fun WeatherIconBasedOnCodeRainyNightPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 65,
        isDay = false
    )
}
@Preview
@Composable
private fun WeatherIconBasedOnCodeThunderStormDayPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 99,
        isDay = true
    )
}

@Preview
@Composable
private fun WeatherIconBasedOnCodeThunderStormNightPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 99,
        isDay = false
    )
}

@Preview
@Composable
private fun WeatherIconBasedOnCodeClearSkyDayPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 0,
        isDay = true
    )
}

@Preview
@Composable
private fun WeatherIconBasedOnCodeClearSkyNightPreview() {
    WeatherIconBasedOnCode(
        weatherCode = 0,
        isDay = false
    )
}
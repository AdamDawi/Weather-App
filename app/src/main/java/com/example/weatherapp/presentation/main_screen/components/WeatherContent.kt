package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    currentDate: String,
    weatherData: Weather,
    location: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Last update time: $currentDate",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(8.dp),
                color = Color.Gray
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
                fontSize = 22.sp,
                color = Color.Black
            )
        }
        Text(
            text = "latitude: ${weatherData.latitude}, longitude: ${weatherData.longitude}",
            color = Color.Black
        )
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "${weatherData.current.temperature_2m} ${weatherData.current_units.temperature_2m}",
            fontSize = 72.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Text(
            text = "Rain: ${weatherData.current.rain}",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "Wind speed: ${weatherData.current.wind_speed_10m} ${weatherData.current_units.wind_speed_10m}",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "Is day: ${weatherData.current.is_day}",
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "Cloud cover: ${weatherData.current.cloud_cover} ${weatherData.current_units.cloud_cover}",
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}


@Preview
@Composable
private fun WeatherContentPreview() {
    WeatherContent(
        currentDate = mockCurrentDate,
        weatherData = mockWeather.toWeather(),
        location = "Lublin"
    )
}
package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.domain.model.Weather
import java.time.LocalDateTime
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DailyWeatherCard(
    modifier: Modifier = Modifier,
    weatherData: Weather,
    index: Int
) {
    Box(
        modifier = modifier
            .height(310.dp)
    ){
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
                .padding(16.dp)
                .padding(top = 8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            WeatherIconBasedOnCode(
                weatherCode = weatherData.daily.weather_code[index],
                isDay = if(index == 0 ) weatherData.current.is_day == 1 else true
            )
            Text(
                modifier = Modifier
                    .padding(top = 170.dp),
                text = "${weatherData.daily.temperature_2m_max[index].roundToInt()}${weatherData.daily_units.temperature_2m_max}",
                fontSize = 62.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Box(
            modifier = Modifier
                .shadow(1.dp, CircleShape)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.7f))

        ){
            Text(
                modifier = Modifier.padding(8.dp),
                text = "${LocalDateTime.parse(weatherData.daily.time[index]+"T00:00").dayOfWeek.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }}, " +
                        "${LocalDateTime.parse(weatherData.daily.time[index]+"T00:00").dayOfMonth} " +
                        LocalDateTime.parse(weatherData.daily.time[index]+"T00:00").month.toString().lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                ,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }
    }
}
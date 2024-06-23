package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.ui.theme.LightOrange
import com.example.weatherapp.presentation.ui.theme.RedPink
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
fun CurrentTemperatureChartCard(
    modifier: Modifier = Modifier,
    weatherData: Weather,
    currentDateTime: LocalDateTime = LocalDateTime.now()
) {
    val onPrimaryContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
    val fullDayTemperatureData = weatherData.hourly.temperature_2m
    val fullDayTimeData = weatherData.hourly.time

    // Finding the hour indices that correspond to the last 7 hours
    val filteredIndices = fullDayTimeData.indices.filter { index ->
        val dateTime = LocalDateTime.parse(fullDayTimeData[index])
        val hourDifference = ChronoUnit.HOURS.between(dateTime, currentDateTime)
        hourDifference in 0..6
    }.takeLast(7)

    // Filtering temperature data based on found indexes
    val filteredData = filteredIndices.map { index ->
        fullDayTemperatureData[index].toFloat()
    }

    // Filtering time data based on found indexes
    val filteredTime = filteredIndices.map { index ->
        LocalDateTime.parse(fullDayTimeData[index]).hour
    }

    // we only take max temperature up to midnight
    val maxValue = weatherData.daily.temperature_2m_max[2].toFloat()
    // search min temperature in 24h
    val minValue = fullDayTemperatureData.minOrNull()?.toFloat() ?: 0f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .shadow(2.dp, shape = RoundedCornerShape(40.dp))
            .height(200.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = "${weatherData.current.temperature_2m.roundToInt()}${weatherData.hourly_units.temperature_2m}",
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Steps for drawing components
                val horizontalStep = canvasWidth / (filteredData.size - 1)
                val verticalStep = (canvasHeight - 40.dp.toPx()) / (maxValue - minValue)

                // Hour markers on the X axis
                filteredData.indices.forEach { index ->
                    val actualHour = filteredTime[index]
                    val x = index * horizontalStep
                    val hourLabel = "$actualHour:00"

                    drawContext.canvas.nativeCanvas.drawText(
                        hourLabel,
                        x,
                        canvasHeight,
                        android.graphics.Paint().apply {
                            color = onPrimaryContainerColor.toArgb()
                            textSize = 24f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }

                // Max temperature on the dashed line
                listOf(maxValue).forEach { temp ->
                    val y = canvasHeight - 24.dp.toPx() - (temp - minValue) * verticalStep
                    drawContext.canvas.nativeCanvas.drawText(
                        "${temp.roundToInt()}°C",
                        0f,
                        y-10f,
                        android.graphics.Paint().apply {
                            color = onPrimaryContainerColor.toArgb()
                            textSize = 24f
                            textAlign = android.graphics.Paint.Align.LEFT
                        }
                    )
                }

                // Dashed line for maximum temperature
                val maxTempY = canvasHeight - 24.dp.toPx() - (maxValue - minValue) * verticalStep
                drawLine(
                    color = RedPink,
                    start = Offset(0f, maxTempY),
                    end = Offset(canvasWidth, maxTempY),
                    strokeWidth = 2.dp.toPx(),
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                // Label for maximum temperature
                drawContext.canvas.nativeCanvas.drawText(
                    "Max Temp",
                    canvasWidth - 100f,
                    maxTempY - 10f,
                    android.graphics.Paint().apply {
                        color = RedPink.toArgb()
                        textSize = 24f
                        textAlign = android.graphics.Paint.Align.RIGHT
                    }
                )
                // The main line of the chart
                val path = Path()
                filteredData.forEachIndexed { index, value ->
                    val x = index * horizontalStep
                    val y = canvasHeight - 20.dp.toPx() - (value - minValue) * verticalStep

                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        val prevX = (index - 1) * horizontalStep
                        val prevY = canvasHeight - 20.dp.toPx() - (filteredData[index - 1] - minValue) * verticalStep
                        path.quadraticBezierTo(prevX, prevY, x, y)
                    }
                }

                // Points on the chart
                drawPath(
                    path = path,
                    color = Color.Yellow.copy(alpha = 0.7f),
                    style = Stroke(width = 2.dp.toPx())
                )
                filteredData.indices.forEach { index ->
                    val x = index * horizontalStep
                    val y = canvasHeight - 20.dp.toPx() - (filteredData[index] - minValue) * verticalStep

                    drawCircle(
                        color = LightOrange.copy(alpha = 0.7f),
                        radius = 3.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TodayWeatherChartCardLateHoursPreview() {
    WeatherAppTheme {
        Surface {
            CurrentTemperatureChartCard(
                weatherData = mockWeather
                    .toWeather(),
                currentDateTime = LocalDateTime.parse("2024-06-23T21:30")
            )
        }
    }
}
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TodayWeatherChartCardEarlyHoursPreview() {
    WeatherAppTheme {
        Surface {
            CurrentTemperatureChartCard(
                weatherData = mockWeather
                    .toWeather(),
                currentDateTime = LocalDateTime.parse("2024-06-23T03:00")
            )
        }
    }
}
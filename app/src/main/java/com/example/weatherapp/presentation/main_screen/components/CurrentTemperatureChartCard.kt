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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.getLastSevenHoursIndices
import com.example.weatherapp.common.mockCurrentLocalDateTime
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.presentation.ui.theme.LightOrange
import com.example.weatherapp.presentation.ui.theme.RedPink
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Composable
fun CurrentTemperatureChartCard(
    modifier: Modifier = Modifier,
    currentTemperature: Double,
    currentTemperatureUnit: String,
    maxTemperatureToday: Double,
    fullDayTemperatureData: List<Double>,
    fullDayTimeData: List<String>,
    currentLocalDateTime: LocalDateTime
) {
    val onPrimaryContainerColor = MaterialTheme.colorScheme.onPrimaryContainer

    // Finding the hour indices that correspond to the last 7 hours
    val lastSevenHoursIndices = getLastSevenHoursIndices(fullDayTimeData, currentLocalDateTime)

    // Filtering temperature data based on found indexes
    val filteredData = filterTemperatureData(fullDayTemperatureData, lastSevenHoursIndices)

    // Filtering time data based on found indexes
    val filteredTime = filterTimeData(fullDayTimeData, lastSevenHoursIndices)

    // Only take max temperature up to midnight
    val maxValue = maxTemperatureToday.toFloat()
    // Search min temperature in 24h
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
            text = "${currentTemperature.roundToInt()}${currentTemperatureUnit}",
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
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
                    color = LightOrange.copy(alpha = 0.7f),
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

// Function to filter temperature data based on indices
private fun filterTemperatureData(
    fullDayTemperatureData: List<Double>,
    indices: List<Int>
): List<Float> {
    return indices.map { index ->
        fullDayTemperatureData[index].toFloat()
    }
}

// Function to filter time data based on indices
private fun filterTimeData(
    fullDayTimeData: List<String>,
    indices: List<Int>
): List<Int> {
    return indices.map { index ->
        LocalDateTime.parse(fullDayTimeData[index]).hour
    }
}


@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TodayWeatherChartCardLateHoursPreview() {
    WeatherAppTheme {
        Surface {
            CurrentTemperatureChartCard(
                currentTemperature = mockWeather.current.temperature_2m,
                currentTemperatureUnit = mockWeather.current_units.temperature_2m,
                maxTemperatureToday = mockWeather.daily.temperature_2m_max[2],
                fullDayTemperatureData = mockWeather.hourly.temperature_2m,
                fullDayTimeData = mockWeather.hourly.time,
                currentLocalDateTime = LocalDateTime.parse(mockCurrentLocalDateTime)
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
                currentTemperature = mockWeather.current.temperature_2m,
                currentTemperatureUnit = mockWeather.current_units.temperature_2m,
                maxTemperatureToday = mockWeather.daily.temperature_2m_max[2],
                fullDayTemperatureData = mockWeather.hourly.temperature_2m,
                fullDayTimeData = mockWeather.hourly.time,
                currentLocalDateTime = LocalDateTime.parse(mockCurrentLocalDateTime)
            )
        }
    }
}
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.mockWeather
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.ui.theme.LightOrange
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.util.Calendar
import kotlin.math.roundToInt

@Composable
fun TodayWeatherChartCard(
    modifier: Modifier = Modifier,
    weatherData: Weather,
    currentHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
) {
    val fullDayTemperatureData = weatherData.hourly.temperature_2m
    var filteredData = fullDayTemperatureData.take(currentHour + 1).map { it.toFloat() }
    val startHour = if (currentHour >= 7) currentHour - 7 else 0
    filteredData = filteredData.slice(startHour..currentHour)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "${weatherData.current.temperature_2m.roundToInt()}${weatherData.hourly_units.temperature_2m}",
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold
        )
        // we only take max temperature up to midnight
        val maxValue = weatherData.daily.temperature_2m_max[2].toFloat()
        // search min temperature in 24h
        val minValue = fullDayTemperatureData.minOrNull()?.toFloat() ?: 0f

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

                val horizontalStep = canvasWidth / (filteredData.size - 1)
                val verticalStep = (canvasHeight - 40.dp.toPx()) / (maxValue - minValue)


                // Hour markers on the X axis
                filteredData.indices.forEach { index ->
                    val actualHour = startHour + index
                    val x = index * horizontalStep
                    val hourLabel = "$actualHour:00"

                    drawContext.canvas.nativeCanvas.drawText(
                        hourLabel,
                        x,
                        canvasHeight,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 24f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }

                // Max temperature on the dashed line
                listOf(maxValue).forEach { temp ->
                    val y = canvasHeight - 24.dp.toPx() - (temp - minValue) * verticalStep
                    drawContext.canvas.nativeCanvas.drawText(
                        "${temp}°C",
                        0f,
                        y-10f,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 24f
                            textAlign = android.graphics.Paint.Align.LEFT
                        }
                    )
                }

                // Dashed line for maximum temperature
                val maxTempY = canvasHeight - 24.dp.toPx() - (maxValue - minValue) * verticalStep
                drawLine(
                    color = Color.Red,
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
                        color = android.graphics.Color.RED
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
private fun TodayWeatherChartCardPreview() {
    WeatherAppTheme {
        Surface {
            TodayWeatherChartCard(
                weatherData = mockWeather
                    .toWeather(),
                currentHour = 12
            )
        }
    }
}
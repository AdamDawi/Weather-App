package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SunsetSunriseCard(
    modifier: Modifier = Modifier,
    sunrise: String,
    sunset: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
    ) {
        Canvas(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val radius = canvasWidth / 4
            val centerX = canvasWidth / 2
            val centerY = canvasHeight
            drawArc(
                color = Color.Yellow,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = Stroke(width = 2.dp.toPx())
            )
            // Draw sun
            val sunRadius = 5.dp.toPx()
            val sunCenterY = centerY - radius

            drawCircle(
                color = Color.Yellow,
                radius = sunRadius,
                center = Offset(centerX, sunCenterY),
                style = Stroke(width = 2.dp.toPx())
            )

            // Draw sun rays
            val numRays = 12
            val rayLength = 5.dp.toPx()
            val rayWidth = 2.dp.toPx()
            val angleStep = 360f / numRays

            for (i in 0 until numRays) {
                val angle = angleStep * i
                rotate(degrees = angle, pivot = Offset(centerX, sunCenterY)) {
                    drawRect(
                        color = Color.Yellow,
                        topLeft = Offset(centerX - rayWidth / 2, sunCenterY - sunRadius - rayLength),
                        size = androidx.compose.ui.geometry.Size(rayWidth, rayLength)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sunrise"
                )
                Text(
                    text = sunrise,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sunset"
                )
                Text(
                    text = sunset,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}

@Preview
@Composable
private fun SunsetSunriseCardPreview() {
    SunsetSunriseCard(
        sunrise = "04:13",
        sunset = "20:48"
    )
}
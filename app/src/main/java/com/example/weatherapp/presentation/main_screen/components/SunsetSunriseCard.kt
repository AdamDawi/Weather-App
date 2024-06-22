package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import android.graphics.LinearGradient
import android.graphics.PathMeasure
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun SunsetSunriseCard(
    modifier: Modifier = Modifier,
    sunrise: String,
    sunset: String,
    sunPathProgressPercent: Float
) {
    Column(
        modifier = modifier
            .shadow(2.dp, shape = RoundedCornerShape(40.dp))
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
            val clampedPercent = sunPathProgressPercent.coerceIn(0f, 100f)

            val path = Path().apply {
                moveTo(canvasWidth.times(.08f), canvasHeight.times(0.96f))
                cubicTo(
                    canvasWidth.times(.10f),
                    -100f,
                    canvasWidth.times(.90f),
                    -100f,
                    canvasWidth.times(0.92f),
                    canvasHeight.times(0.96f)
                )
            }

            val path2 = Path().apply {
                moveTo(canvasWidth.times(0f), canvasHeight.times(0.96f))
                lineTo(canvasWidth.times(1f), canvasHeight.times(0.96f))
            }

            // Draw the path with gradient stroke
            drawIntoCanvas { canvas ->
                val gradientStartColor = Color.Yellow
                val gradientEndColor = Color.LightGray
                // Calculate gradient points
                val gradientStart = getPositionOnPath(path, clampedPercent-0.01f) // Slightly before sun
                val gradientEnd = getPositionOnPath(path, clampedPercent)

                val paint = android.graphics.Paint().apply {
                    shader = LinearGradient(
                        gradientStart[0],
                        gradientStart[1],
                        gradientEnd[0],
                        gradientEnd[1],
                        intArrayOf(gradientStartColor.copy(alpha = 0.7f).toArgb(), gradientEndColor.copy(alpha = 0.7f).toArgb()),
                        floatArrayOf(0.5f,0.5f),
                        android.graphics.Shader.TileMode.CLAMP
                    )
                    strokeWidth = 2.dp.toPx()
                    style = android.graphics.Paint.Style.STROKE
                }
                canvas.nativeCanvas.drawPath(path.asAndroidPath(), paint)
            }

            drawPath(
                path = path2,
                style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round),
                color = Color.LightGray.copy(alpha = 0.7f)
            )

            // draw sun
            val sunRadius = 8.dp.toPx()
            val position = getPositionOnPath(path, clampedPercent)

            // sun shadow
            drawCircle(
                color = Color.Yellow.copy(alpha = 0.5f),
                radius = sunRadius+3.dp.toPx(),
                center = Offset(position[0], position[1]),
            )

            // sun
            drawCircle(
                color = Color.Yellow,
                radius = sunRadius,
                center = Offset(position[0], position[1]),
            )
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

// calculate position on path
private fun getPositionOnPath(path: Path, percent: Float): FloatArray {
    val pathMeasure = PathMeasure(path.asAndroidPath(), false)
    val pathLength = pathMeasure.length

    val position = FloatArray(2)
    pathMeasure.getPosTan(pathLength * percent / 100f, position, null)
    return position
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SunsetSunriseCardHighPercentPreview() {
    WeatherAppTheme {
        Surface {
            SunsetSunriseCard(
                sunrise = "04:13",
                sunset = "20:48",
                sunPathProgressPercent = 100f
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SunsetSunriseCardLowPercentPreview() {
    WeatherAppTheme {
        Surface {
            SunsetSunriseCard(
                sunrise = "04:13",
                sunset = "20:48",
                sunPathProgressPercent = 20f
            )
        }
    }
}
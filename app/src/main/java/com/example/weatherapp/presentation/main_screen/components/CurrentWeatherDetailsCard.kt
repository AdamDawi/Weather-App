package com.example.weatherapp.presentation.main_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun CurrentWeatherDetailsCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String?,
    icon: Painter
) {
   Box(modifier = modifier
       .shadow(2.dp, shape = RoundedCornerShape(12.dp))
       .background(
           color = MaterialTheme.colorScheme.primaryContainer,
           shape = RoundedCornerShape(12.dp)
       )
       .size(140.dp, 80.dp)
       .padding(8.dp)

   ){
         Column(
             modifier = Modifier.fillMaxSize(),
             verticalArrangement = Arrangement.SpaceEvenly,
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = icon,
                contentDescription = icon.toString(),
                tint = Color.Unspecified
            )
             Text(
                 text = "$value ${unit.orEmpty()}",
                 fontSize = 18.sp,
                 fontWeight = FontWeight.SemiBold,
                 color = MaterialTheme.colorScheme.onPrimaryContainer
             )
             Text(
                 text = title,
                 fontSize = 8.sp,
                 color = MaterialTheme.colorScheme.onSecondaryContainer
             )
         }
   }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CardForDetailRainPreview() {
    WeatherAppTheme {
        Surface {
            CurrentWeatherDetailsCard(
                title = "Rain",
                value = "2.0",
                unit = "mm",
                icon = painterResource(id = R.drawable.ic_rain)
            )
        }
    }
}
@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CardForDetailHumidityPreview() {
    WeatherAppTheme {
        Surface {
            CurrentWeatherDetailsCard(
                title = "Rain",
                value = "46.0",
                unit = "%",
                icon = painterResource(id = R.drawable.ic_humidity_medium)
            )
        }
    }
}
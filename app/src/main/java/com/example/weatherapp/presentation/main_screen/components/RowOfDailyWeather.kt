package com.example.weatherapp.presentation.main_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.common.mockDaily
import com.example.weatherapp.common.mockDailyUnits
import com.example.weatherapp.data.remote.dto.Daily
import com.example.weatherapp.data.remote.dto.DailyUnits
import java.time.LocalDateTime

@Composable
fun RowOfDailyWeather(
    modifier: Modifier = Modifier,
    daily: Daily,
    dailyUnits: DailyUnits
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        itemsIndexed(daily.time) { index, _ ->
            CardForDetail(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = "${LocalDateTime.parse(daily.time[index]+"T00:00").dayOfWeek}",
                value = daily.temperature_2m_max[index].toString(),
                icon = painterResource(id = R.drawable.cloudy_day),
                unit = dailyUnits.temperature_2m_max
            )
        }
    }
}

@Preview
@Composable
private fun RowOfDailyWeatherPreview() {
    RowOfDailyWeather(
        daily = mockDaily,
        dailyUnits = mockDailyUnits
    )
}
package com.example.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.weatherapp.presentation.main_screen.MainScreen
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            var darkTheme by remember {
                mutableStateOf(isSystemInDarkTheme)
            }
            WeatherAppTheme(darkTheme = darkTheme){
                Surface(
                    color = MaterialTheme.colorScheme.surface) {
                    MainScreen(
                        onThemeUpdate = { darkTheme = !darkTheme},
                        darkTheme = darkTheme
                    )
                }
            }
        }
    }
}

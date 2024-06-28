package com.example.weatherapp.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.weatherapp.di.AppModule
import com.example.weatherapp.presentation.main_screen.MainScreen
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class FoodEndToEndTest {
    //ensure that we can actually call an inject function before every test case
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            var darkTheme by remember {
                mutableStateOf(isSystemInDarkTheme)
            }
            WeatherAppTheme(darkTheme = darkTheme) {
                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MainScreen(
                        onThemeUpdate = { darkTheme = !darkTheme },
                        isDarkTheme = darkTheme
                    )
                }
            }
        }
    }

    @Test
    fun test() {
        composeRule.onNodeWithText("Last").assertExists()
    }
}
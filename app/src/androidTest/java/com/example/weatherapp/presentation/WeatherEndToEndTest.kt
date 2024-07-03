package com.example.weatherapp.presentation

import android.content.Context
import android.os.Build
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.mockCurrent
import com.example.weatherapp.common.mockCurrentUnits
import com.example.weatherapp.common.mockDaily
import com.example.weatherapp.common.provideFormattedTimeToDayOfWeekDate
import com.example.weatherapp.di.AppModule
import com.example.weatherapp.presentation.main_screen.MainScreen
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import kotlin.math.roundToInt

@HiltAndroidTest
@UninstallModules(AppModule::class)
@LargeTest
class FoodEndToEndTest {
    //ensure that call an inject function before every test case
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //launch a new component activity
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var darkTheme: MutableState<Boolean>

    @Before
    fun setUp() = runTest{
        hiltRule.inject()
        composeRule.activity.setContent {
             darkTheme = remember {
                mutableStateOf(false)
            }
            WeatherAppTheme(darkTheme = darkTheme.value) {
                Surface(
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MainScreen(
                        onThemeUpdate = { darkTheme.value = !darkTheme.value },
                        isDarkTheme = darkTheme.value
                    )
                }
            }
        }
        // grant location permissions for test
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            getInstrumentation().uiAutomation.executeShellCommand("pm grant " + getApplicationContext<Context>().packageName + " android.permission.ACCESS_FINE_LOCATION")
            getInstrumentation().uiAutomation.executeShellCommand("pm grant " + getApplicationContext<Context>().packageName + " android.permission.ACCESS_COARSE_LOCATION")
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldShowCorrectLocationWhenLocationFetchIsSuccessful() = runTest {
        composeRule.awaitIdle()
        composeRule.waitUntilAtLeastOneExists(hasText("Poland, Warszawa"))
        composeRule.onNodeWithText("Poland, Warszawa", useUnmergedTree = true, substring = true, ignoreCase = true).assertExists()
    }

    @Test
    fun shouldShowCorrectDisplayedDayOfWeekWhenWeatherFetchIsSuccessful() = runTest {
        composeRule.awaitIdle()
        val currentTime = provideFormattedTimeToDayOfWeekDate(mockDaily.time[2])
        composeRule.onNodeWithText(currentTime).assertExists()
    }

    @Test
    fun shouldShowCorrectCurrentRoundedTemperatureWhenWeatherFetchIsSuccessful() = runTest {
        composeRule.awaitIdle()
        val currentTemperature = mockCurrent.temperature_2m.roundToInt().toString() + mockCurrentUnits.temperature_2m
        composeRule.onNodeWithText(currentTemperature).assertExists()
    }

    @Test
    fun shouldShowCorrectSunriseTimeWhenWeatherFetchIsSuccessful() = runTest {
        composeRule.awaitIdle()
        val localDateTime = LocalDateTime.parse(mockDaily.sunrise[2])
        val currentSunriseTime = "${localDateTime.hour}:${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}"
        composeRule.onNodeWithText(currentSunriseTime).assertExists()
    }

    @Test
    fun shouldShowCorrectSunsetTimeWhenWeatherFetchIsSuccessful() = runTest {
        composeRule.awaitIdle()
        val localDateTime = LocalDateTime.parse(mockDaily.sunset[2])
        val currentSunsetTime = "${localDateTime.hour}:${if (localDateTime.minute < 10) "0" + localDateTime.minute else localDateTime.minute}"
        composeRule.onNodeWithText(currentSunsetTime).assertExists()
    }

    @Test
    fun clickOnThemeButtonShouldChangeTheme() = runTest {
        composeRule.awaitIdle()
        Assert.assertEquals(darkTheme.value, false)
        composeRule.onNodeWithTag(Constants.THEME_SWITCHER).performClick()
        Assert.assertEquals(darkTheme.value, true)
    }

}
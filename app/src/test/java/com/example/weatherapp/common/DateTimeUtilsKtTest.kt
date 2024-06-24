package com.example.weatherapp.common

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DateTimeUtilsKtTest {

    // region constants

    // endregion constants

    // region helper fields

    // endregion helper fields

    @Before
    fun setup() {
    }

    @Test
    fun provideFormattedTimeToDayOfWeekDate_correctTimePassed_correctStringReturned() {
        // Arrange
        val time = "2024-06-24"
        // Act
        val result = provideFormattedTimeToDayOfWeekDate(time)
        // Assert
        assertEquals(result, "Monday, 24 June")
    }

    @Test
    fun provideFormattedTimeToDayOfWeekDate_incorrectTimePassed_exceptionReturned() {
        // Arrange
        val time = "2024-24-06"
        // Act
        try {
            provideFormattedTimeToDayOfWeekDate(time)
            fail("Expected IllegalArgumentException to be thrown")
        }catch(e: IllegalArgumentException){
            // Assert
            assertEquals("Invalid time format: $time. Expected format is YYYY-MM-DD.", e.message)
        }
    }

    @Test
    fun provideFormattedTimeToHourMinuteSecond_correctTimePassed_correctStringReturned() {
        // Arrange
        val time = "2024-06-24T15:30:45"
        // Act
        val result = provideFormattedTimeToHourMinuteSecond(time)
        // Assert
        assertEquals(result, "15:30:45")
    }

    @Test
    fun provideFormattedTimeToHourMinuteSecond_incorrectTimePassed_exceptionReturned() {
        // Arrange
        val time = "2024-24-06T15:30:45"
        // Act
        try {
            provideFormattedTimeToHourMinuteSecond(time)
            fail("Expected IllegalArgumentException to be thrown")
        }catch(e: IllegalArgumentException){
            // Assert
            assertEquals("Invalid time format: $time. Please provide a valid time string.", e.message)
        }
    }

    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes
}
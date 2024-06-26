package com.example.weatherapp.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

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

    @Test
    fun getLastSevenHoursIndices_correctFullDayTimeWithin1DayDataPassed_correctListReturned() {
        // Arrange
        val fullDayTimeData = listOf(
            "2024-06-25T00:00:00",
            "2024-06-25T01:00:00",
            "2024-06-25T02:00:00",
            "2024-06-25T03:00:00",
            "2024-06-25T04:00:00",
            "2024-06-25T05:00:00",
            "2024-06-25T06:00:00",
            "2024-06-25T07:00:00",
            "2024-06-25T08:00:00",
            "2024-06-25T09:00:00",
            "2024-06-25T10:00:00",
            "2024-06-25T11:00:00"
        )
        val currentDateTime = LocalDateTime.parse("2024-06-25T11:00:00")
        // Act
        val result = getLastSevenHoursIndices(fullDayTimeData, currentDateTime)
        // Assert
        assertEquals(listOf(5, 6, 7, 8, 9, 10, 11), result)
    }

    @Test
    fun getLastSevenHoursIndices_correctFullDayTimeWithin2DaysDataPassed_correctListReturned() {
        // Arrange
        val currentDateTime = LocalDateTime.parse("2024-06-22T03:00:00")
        // Act
        val result = getLastSevenHoursIndices(mockHourly.time, currentDateTime)
        // Assert
        assertEquals(listOf(21, 22, 23, 24, 25, 26, 27), result)
    }

    @Test
    fun getLastSevenHoursIndices_correctCurrentDataTimeWithMinutesPassed_correctListReturned() {
        // Arrange
        val currentDateTime = LocalDateTime.parse("2024-06-22T03:26:00")
        // Act
        val result = getLastSevenHoursIndices(mockHourly.time, currentDateTime)
        // Assert
        assertEquals(listOf(22, 23, 24, 25, 26, 27, 28), result)
    }

    @Test
    fun getLastSevenHoursIndices_incorrectCurrentDataTimePassed_exceptionReturned() {
        // Arrange
        val currentDateTime = LocalDateTime.parse("2024-06-22T03:26:00")
        val fullDayTimeData = listOf(
            "2024-06-25T00:00:00",
            "2024-06-25T01:00:00",
            "2024-06-25T02:00:00",
            "2024-25-06T03:00:00",
            "2024-06-25T04:00:00",
            "2024-06-25T05:00:00",
            "2024-06-25T06:00:00",
            "2024-06-25T07:00:00",
            "2024-06-25T08:00:00",
            "2024-06-25T09:00:00",
            "2024-06-25T10:00:00",
            "2024-06-25T11:00:00"
        )
        // Act
        try {
            getLastSevenHoursIndices(fullDayTimeData, currentDateTime)
            fail("Expected IllegalArgumentException to be thrown")
        }catch(e: IllegalArgumentException){
            // Assert
            assertTrue(e.cause is DateTimeParseException)
            assertEquals(
                "Invalid time format at index 3: 2024-25-06T03:00:00. Please provide a valid time string.",
                e.message
            )
        }
    }
    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes
}
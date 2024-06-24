package com.example.weatherapp.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * Formats a given date string into a readable format.
 *
 * The input date string should be in the format "YYYY-MM-DD".
 * The output will be in the format "day of the week, day of the month month".
 *
 * Example: "2024-06-24" will be formatted as "Monday, 24 June".
 *
 * @param time The date string to format. Expected format is "YYYY-MM-DD".
 * @return A formatted date string in the format "day of the week, day of the month month".
 *
 * @throws DateTimeParseException if the input date string cannot be parsed.
 */
fun provideFormattedTimeToDayOfWeekDate(time: String): String {
    return try {
        val localDateTime = LocalDateTime.parse(time+"T00:00")

        val dayOfWeek = localDateTime.dayOfWeek.toString().lowercase()

            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val dayOfMonth = localDateTime.dayOfMonth

        val month = localDateTime.month.toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        "$dayOfWeek, $dayOfMonth $month"
    } catch (e: DateTimeParseException) {
        throw IllegalArgumentException("Invalid time format: $time. Expected format is YYYY-MM-DD.", e)
    }
}

/**
 * Formats a given time string to the "HH:mm:ss" pattern.
 *
 * This function takes a time string, parses it into a LocalDateTime object,
 * and then formats it into a string with the pattern "HH:mm:ss" which represents
 * hours, minutes, and seconds.
 *
 * @param time A string representing the time in a format parsable by LocalDateTime.
 * @return A formatted time string in the "HH:mm:ss" pattern.
 * @throws DateTimeParseException if the time string cannot be parsed.
 *
 * Example usage:
 * ```
 * val formattedTime = provideFormattedTimeToHourMinuteSecond("2024-06-24T15:30:45")
 * println(formattedTime) // Output: 15:30:45
 * ```
 */
fun provideFormattedTimeToHourMinuteSecond(time: String): String{
    return try {
        LocalDateTime.parse(time).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    } catch (e: DateTimeParseException) {
        throw IllegalArgumentException("Invalid time format: $time. Please provide a valid time string.", e)
    }
}


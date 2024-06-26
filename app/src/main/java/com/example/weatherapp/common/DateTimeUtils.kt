package com.example.weatherapp.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
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

/**
 * Returns a list of indices representing the last seven hours from the given currentDateTime.
 *
 * This function filters the indices of the provided fullDayTimeData list, which contains
 * datetime strings, to only include those within the last seven hours relative to the
 * currentDateTime. The resulting list is ordered from the earliest to the latest hour within
 * that range.
 *
 * @param fullDayTimeData A list of datetime strings in ISO_LOCAL_DATE_TIME format.
 * @param currentDateTime The current datetime to compare against.
 * @return A list of indices corresponding to the last seven hours in the fullDayTimeData list.
 *         The list is ordered from the earliest to the latest within the seven-hour range.
 *
 * Example usage:
 * ```
 * val fullDayTimeData = listOf(
 *     "2024-06-25T00:00:00",
 *     "2024-06-25T01:00:00",
 *     "2024-06-25T02:00:00",
 *     "2024-06-25T03:00:00",
 *     "2024-06-25T04:00:00",
 *     "2024-06-25T05:00:00",
 *     "2024-06-25T06:00:00",
 *     "2024-06-25T07:00:00",
 *     "2024-06-25T08:00:00",
 *     "2024-06-25T09:00:00",
 *     "2024-06-25T10:00:00",
 *     "2024-06-25T11:00:00"
 * )
 * val currentDateTime = LocalDateTime.parse("2024-06-25T11:00:00")
 * val indices = getLastSevenHoursIndices(fullDayTimeData, currentDateTime)
 * println(indices) // Output: [4, 5, 6, 7, 8, 9, 10]
 *
 * val currentDateTime2 = LocalDateTime.parse("2024-06-25T07:30:00")
 * val indices2 = getLastSevenHoursIndices(fullDayTimeData, currentDateTime2)
 * println(indices2) // Output: [1, 2, 3, 4, 5, 6, 7]
 *
 * val currentDateTime3 = LocalDateTime.parse("2024-06-25T04:00:00")
 * val indices3 = getLastSevenHoursIndices(fullDayTimeData, currentDateTime3)
 * println(indices3) // Output: [0, 1, 2, 3, 4]
 * ```
 */
fun getLastSevenHoursIndices(
    fullDayTimeData: List<String>,
    currentDateTime: LocalDateTime
): List<Int> {
    return fullDayTimeData.indices.reversed().filter { index ->
        try {
            val dateTime = LocalDateTime.parse(fullDayTimeData[index])
            ChronoUnit.HOURS.between(dateTime, currentDateTime) in 0..6
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Invalid time format at index $index: ${fullDayTimeData[index]}. Please provide a valid time string.", e)
        }
    }.take(7).reversed()
}


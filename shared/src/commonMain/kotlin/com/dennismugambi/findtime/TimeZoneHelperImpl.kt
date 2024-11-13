package com.dennismugambi.findtime

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TimeZoneHelperImpl: TimeZoneHelper {
    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted()
    }

    override fun currentTime(): String {

        val currentMoment: Instant = Clock.System.now()

        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        return formatDateTime(dateTime)
    }

    override fun currentTimeZone(): String {

        val currentTimeZone = TimeZone.currentSystemDefault()
        return currentTimeZone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {

        val currentTimeZone = TimeZone.currentSystemDefault()
        val currentUTCInstant: Instant = Clock.System.now()
        val otherTimeZone = TimeZone.of(otherTimeZoneId)
        val currentDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(currentTimeZone)
        // 5
        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        // 6
        return abs((currentDateTime.hour - currentOtherDateTime.hour) * 1.0)
    }

    override fun getTime(timeZoneId: String): String {

        val timezone = TimeZone.of(timeZoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)

        return formatDateTime(dateTime)
    }

    override fun getDate(timeZoneId: String): String {

        val timeZone = TimeZone.of(timeZoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timeZone)

        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, " +

        "${dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${dateTime.date.dayOfMonth}"
    }

    override fun search(startHour: Int, endHour: Int, timeZoneStrings: List<String>): List<Int> {

        val goodHours = mutableListOf<Int>()

        val timeRange = IntRange(max(0, startHour), min(23, endHour))

        val currentTimeZone = TimeZone.currentSystemDefault()

        for (hour in timeRange) {
            var isGoodHour = false
            // 5
            for (zone in timeZoneStrings) {
                val timezone = TimeZone.of(zone)
                // 6
                if (timezone == currentTimeZone) {
                    continue
                }
                // 7
                if (!isValid(
                        timeRange = timeRange,
                        hour = hour,
                        currentTimeZone = currentTimeZone,
                        otherTimeZone = timezone
                    )
                ) {
                    Napier.d("Hour $hour is not valid for time range")
                    isGoodHour = false
                    break
                } else {
                    Napier.d("Hour $hour is Valid for time range")
                    isGoodHour = true
                }
            }

            if (isGoodHour) {
                goodHours.add(hour)
            }
        }

        return goodHours

    }

    private fun isValid(timeRange: IntRange, hour: Int, currentTimeZone: TimeZone, otherTimeZone: TimeZone): Boolean {

        if(hour !in timeRange) {
            return false
        }


        val currentUTCInstant: Instant = Clock.System.now()

        val currentOtherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)

        val otherDateTimeWithHour = LocalDateTime(
            currentOtherDateTime.year,
            currentOtherDateTime.monthNumber,
            currentOtherDateTime.dayOfMonth,
            hour,
            0,
            0,
            0
        )


        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)

        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")

        return convertedTime.hour in timeRange

    }

    fun formatDateTime(dateTime: LocalDateTime): String {

        val stringBuilder = StringBuilder()

        val minute = dateTime.minute
        var hour = dateTime.hour % 12
        if (hour == 0) hour = 12

        val amPm = if (dateTime.hour < 12) "AM" else "PM"

        stringBuilder.append(hour.toString())
        stringBuilder.append(":")

        if (minute < 10) {
            stringBuilder.append("0")
        }

        stringBuilder.append(minute.toString())
        stringBuilder.append(amPm)

        return stringBuilder.toString()
    }
}
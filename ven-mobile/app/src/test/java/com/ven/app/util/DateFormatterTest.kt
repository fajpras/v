package com.ven.app.util

import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatterTest {

    @Test
    fun formatRequestMeetingDate_formatsFullDateWithDayOfWeek() {
        val date = LocalDate.of(2026, 9, 30)
        val formatted = DateFormatter.formatRequestMeetingDate(date)
        assertEquals("Wed, 30 September 2026", formatted)
    }

    @Test
    fun formatRequestMeetingDate_singleDigitDay_formattedWithoutLeadingZero() {
        // Pattern "EEE, d MMMM yyyy" -> single digit day has no leading zero
        val date = LocalDate.of(2026, 9, 5)
        val formatted = DateFormatter.formatRequestMeetingDate(date)
        assertEquals("Sat, 5 September 2026", formatted)
    }

    @Test
    fun formatRequestMeetingDate_januaryAndDecember_formattedProperly() {
        val newYear = LocalDate.of(2027, 1, 1)
        assertEquals("Fri, 1 January 2027", DateFormatter.formatRequestMeetingDate(newYear))

        val yearEnd = LocalDate.of(2026, 12, 31)
        assertEquals("Thu, 31 December 2026", DateFormatter.formatRequestMeetingDate(yearEnd))
    }

    @Test
    fun formatMonthYear_formatsMonthNameAndYear() {
        val sept2026 = YearMonth.of(2026, 9)
        assertEquals("September 2026", DateFormatter.formatMonthYear(sept2026))

        val oct2026 = YearMonth.of(2026, 10)
        assertEquals("October 2026", DateFormatter.formatMonthYear(oct2026))

        val jan2027 = YearMonth.of(2027, 1)
        assertEquals("January 2027", DateFormatter.formatMonthYear(jan2027))
    }

    @Test
    fun formatMonthYear_leapYearFebruary_formattedProperly() {
        val feb2028 = YearMonth.of(2028, 2)
        assertEquals("February 2028", DateFormatter.formatMonthYear(feb2028))
    }
}
